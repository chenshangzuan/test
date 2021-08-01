package spark.local;

import com.google.common.collect.Lists;
import org.apache.commons.codec.StringDecoder;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.AnalysisException;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.*;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.glassfish.jersey.internal.guava.Sets;
import scala.Tuple2;

import java.util.*;

/**
 * @author Kled
 * @date 2021/7/1 11:47 上午
 */
public class SparkLocalStreamTest {
    public static void main(String[] args) throws AnalysisException, InterruptedException {
        //socketStream();
        kafkaStream();
    }

    public static void socketStream() throws InterruptedException {
        SparkConf conf = new SparkConf().setAppName("SparkLocalFileStreamTest").setMaster("local[2]");
        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(1));

        //nc -lk 9999
        JavaReceiverInputDStream<String> lines = jssc.socketTextStream("127.0.0.1", 9999);
        //监控文件目录
        //JavaDStream<String> lines = jssc.textFileStream("hdfs://172.16.5.56:9000/stream"); //文件流

        JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String x) {
                return Arrays.stream(x.split(" ")).iterator();
            }
        });

        JavaPairDStream<String, Integer> pairs = words.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) {
                return new Tuple2(s, 1);
            }
        });
        System.out.println(pairs);
        JavaPairDStream<String, Integer> wordCounts = pairs.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer i1, Integer i2) {
                return i1 + i2;
            }
        });

        wordCounts.print();
        jssc.start();
        jssc.awaitTermination();
    }

    public static void kafkaStream() throws InterruptedException {
        SparkConf conf = new SparkConf().setAppName("SparkLocalKafkaStreamTest").setMaster("local[2]");
        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(1));
        jssc.checkpoint("/tmp/sparkCheckpoint");

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
        //按时间窗口聚合计算，每隔1s，窗口大小5s
        JavaPairDStream<String, Long> stringLongJavaPairDStream = directStream.map(record -> record.key() + ":" + record.value()).countByValueAndWindow(Durations.seconds(5), Durations.seconds(1));
        stringLongJavaPairDStream.print();

        jssc.start();
        jssc.awaitTermination();
    }
}
