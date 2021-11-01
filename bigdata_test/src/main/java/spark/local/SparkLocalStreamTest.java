package spark.local;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.apache.commons.codec.StringDecoder;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.Optional;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.Minutes;
import org.apache.spark.streaming.api.java.*;
import org.apache.spark.streaming.flume.FlumeBatchFetcher;
import org.apache.spark.streaming.flume.FlumePollingReceiver;
import org.apache.spark.streaming.flume.FlumeUtils;
import org.apache.spark.streaming.flume.SparkFlumeEvent;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.glassfish.jersey.internal.guava.Sets;
import scala.Tuple2;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.apache.spark.storage.StorageLevel.MEMORY_ONLY_SER_2;

/**
 * @author Kled
 * @date 2021/7/1 11:47 上午
 */
public class SparkLocalStreamTest {
    public static void main(String[] args) throws AnalysisException, InterruptedException {
        Logger.getLogger("org").setLevel(Level.OFF);
        System.setProperty("spark.ui.showConsoleProgress", "false");
        socketStream();
        //queueStream();
        //fileStream();
        //kafkaStream();
        //flumeStream();
    }

    private static void queueStream() throws InterruptedException {
        SparkConf conf = new SparkConf().setAppName("SparkLocalQueueStreamTest").setMaster("local[2]");
        //采样时间是1s
        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(1));

        Queue<JavaRDD<Integer>> queue = new LinkedBlockingQueue<JavaRDD<Integer>>();
        jssc.sparkContext().parallelize(Lists.newArrayList(1,2,3));
        JavaDStream<Integer> queueStream = jssc.queueStream(queue);

        new Thread(() -> {
            queue.add(jssc.sparkContext().parallelize(Lists.newArrayList(1,2,3)));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        queueStream.print();
        jssc.start();
        jssc.awaitTermination();
    }

    private static void fileStream() throws InterruptedException {
        SparkConf conf = new SparkConf().setAppName("SparkLocalFileStreamTest").setMaster("local[2]");
        //采样时间是1s
        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(1));

        //监控文件目录新增的文件
        //JavaDStream<String> lines = jssc.textFileStream("hadoop.hdfs://172.16.5.56:9000/stream"); //文件流
        JavaDStream<String> lines = jssc.textFileStream("/tmp"); //文件流

        JavaDStream<String> words = lines.transform(rdd -> rdd.flatMap(line -> Arrays.stream(line.split(" ")).iterator()));

        //spark sql统计计数
        words.foreachRDD(rdd -> {
            SparkSession sparkSession = SparkSession.builder().sparkContext(rdd.context()).getOrCreate();
            StructType structType = DataTypes.createStructType(Lists.newArrayList(DataTypes.createStructField("word", DataTypes.StringType, true)));
            JavaRDD<Row> rowJavaRDD = rdd.map(RowFactory::create);
            Dataset<Row> dataFrame = sparkSession.createDataFrame(rowJavaRDD, structType);
            dataFrame.createOrReplaceTempView("words");
            sparkSession.sql("select word, count(1) from words group by word").show();
        });

        words.print();
        jssc.start();
        jssc.awaitTermination();
    }

    public static void socketStream() throws InterruptedException {
        SparkConf conf = new SparkConf().setAppName("SparkLocalSocketStreamTest").setMaster("local[2]");
        //采样时间是1s
        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(1));
        //使用updateStateByKey时须指定checkpoint
        jssc.checkpoint("/tmp");

        //nc -lk 9999
        JavaReceiverInputDStream<String> lines = jssc.socketTextStream("127.0.0.1", 9999);

        //JavaDStream<String> words = lines.transform(rdd -> rdd.flatMap(line -> Arrays.stream(line.split(" ")).iterator()));
        JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String x) {
                return Arrays.stream(x.split(" ")).iterator();
            }
        });

        //lines.transformToPair(rdd -> rdd.mapToPair(word -> new Tuple2(word, 1)));
        JavaPairDStream<String, Integer> pairs = words.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) {
                return new Tuple2(s, 1);
            }
        });
        System.out.println(pairs);

//        JavaPairDStream<String, Integer> wordCounts = pairs.reduceByKey(new Function2<Integer, Integer, Integer>() {
//            @Override
//            public Integer call(Integer i1, Integer i2) {
//                return i1 + i2;
//            }
//        });

        //累加统计结果
        JavaPairDStream<String, Integer> wordCounts = pairs.updateStateByKey(new Function2<List<Integer>, Optional<Integer>, Optional<Integer>>() {
            @Override
            public Optional<Integer> call(List<Integer> integers, Optional<Integer> optional) throws Exception {
                int currentValue = integers.stream().reduce(0, Integer::sum);
                int preValue = optional.orElse(0);
                return Optional.of(currentValue + preValue);
            }
        });

        wordCounts.print();
//        wordCounts.foreachRDD();
//        wordCounts.saveAsHadoopFiles();
//        wordCounts.saveAsNewAPIHadoopFiles();
        jssc.start();
        jssc.awaitTermination();

    }

    public static void kafkaStream() throws InterruptedException {
        SparkConf conf = new SparkConf().setAppName("SparkLocalKafkaStreamTest").setMaster("local[2]");
        conf.set("spark.streaming.kafka.maxRatePerPartition", "10000");
//        conf.set("spark.serializer", "org.apache.spark.serializer.kryoSerializer");
//        conf.registerKryoClasses()

        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(1));
        jssc.checkpoint("/tmp/sparkCheckpoint");
        //确保交互查询时，数据不被删除？
        jssc.remember(Minutes.apply(1));

        Set<String> topicsSet = Sets.newHashSet();
        topicsSet.add("Test123");

        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.16.5.56:9092");
//        kafkaParams.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
//        kafkaParams.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        kafkaParams.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaParams.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaParams.put(ConsumerConfig.GROUP_ID_CONFIG, "kled");

        // Create direct kafka stream with brokers and topics
        JavaInputDStream<ConsumerRecord<String, String>> directStream = KafkaUtils.createDirectStream(
                jssc,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.Subscribe(topicsSet, kafkaParams)
        );

        JavaDStream<Object> map = directStream.map(record -> record.key() + ":" + record.value());
        map.print();

        //数据并行接收
        //jssc.union(map....);

        //按时间窗口聚合计算，每隔1s(滑动周期)，窗口大小5s
        //注：滑动周期必须是采样周期的整数倍！！
        JavaPairDStream<String, Long> stringLongJavaPairDStream = directStream.map(record -> record.key() + ":" + record.value()).countByValueAndWindow(Durations.seconds(5), Durations.seconds(1));
        stringLongJavaPairDStream.print();

        jssc.start();
        jssc.awaitTermination();
    }

    private static void flumeStream() throws InterruptedException {
        SparkConf conf = new SparkConf().setAppName("SparkLocalKafkaStreamTest").setMaster("local[2]");
        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(1));

        JavaReceiverInputDStream<SparkFlumeEvent> pollingStream = FlumeUtils.createPollingStream(jssc, "172.16.5.56", 1234, MEMORY_ONLY_SER_2());
//        JavaReceiverInputDStream<SparkFlumeEvent> stream = FlumeUtils.createStream(jssc, "172.16.5.56", 1234, MEMORY_ONLY_SER_2());
        JavaDStream<String> map = pollingStream.map(e -> new String(e.event().getBody().array()));
        map.print();

        jssc.start();
        jssc.awaitTermination();
    }
}
