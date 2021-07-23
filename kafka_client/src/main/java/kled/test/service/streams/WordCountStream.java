package kled.test.service.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.WindowStore;
import org.apache.kafka.streams.state.internals.RocksDBWindowStore;
import org.apache.spark.sql.catalyst.expressions.TimeWindow;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Kled
 * @date 2021/7/19 3:00 下午
 */
public class WordCountStream {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-application");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "172.16.5.56:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();
//        topology(builder);
        windowTopology(builder);

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        //4.启动
        streams.start();
    }

    public static void topology(StreamsBuilder builder){
        //1.定义输入流，指定topic
        KStream<String, String> textLines = builder.stream("TextLinesTopic");

        //2.构建拓扑 DAG
        KTable<String, Long> wordCounts = textLines
                //按空白符切分
                .flatMapValues(textLine -> Arrays.asList(textLine.toLowerCase().split("\\W+")))
                //按单词分组
                //.map((k, v) -> new KeyValue<>(v, v)).groupByKey() 等同于下面
                .groupBy((key, word) -> word)
                //聚合计数
                .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("counts-store"));

//        wordCounts.toStream().print(Printed.toSysOut());
        //3.定义输出流
        wordCounts.toStream().to("WordsWithCountsTopic", Produced.with(Serdes.String(), Serdes.Long()));
    }

    public static void windowTopology(StreamsBuilder builder){
        KStream<String, String> textLines = builder.stream("TextLinesTopic");
        KTable<Windowed<String>, String> wordCountWindow = textLines
                //按空白符切分
                .flatMapValues(textLine -> Arrays.asList(textLine.toLowerCase().split("\\W+")))
                //按单词分组
                //.map((k, v) -> new KeyValue<>(v, v)).groupByKey() 等同于下面
                .groupBy((key, word) -> word)
//                .windowedBy(TimeWindows.of(5000).advanceBy(1000))
//                .count();
                //每隔1s，时间窗口为5s
                .aggregate(() -> "0" , (k, v, count) -> String.valueOf(Integer.parseInt(count) + 1),
                        TimeWindows.of(5000).advanceBy(1000),
                        Serdes.String(),
                        "trace-word-count");
        wordCountWindow.toStream((k, v) -> k.key()).to("WordsWithWindowCountsTopic", Produced.with(Serdes.String(), Serdes.String()));

    }
}
