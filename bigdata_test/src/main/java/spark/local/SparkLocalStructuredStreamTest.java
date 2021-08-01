package spark.local;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.streaming.OutputMode;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.eclipse.jetty.util.ajax.JSON;
import org.json.JSONObject;
import spark.remote.Person;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author Kled
 * @date 2021/7/1 11:47 上午
 */
public class SparkLocalStructuredStreamTest {
    public static void main(String[] args) throws AnalysisException, InterruptedException, TimeoutException, StreamingQueryException {
        SparkSession sparkSession = SparkSession
                .builder()
                .appName("SparkLocalStructuredStreamTest")
                .master("local")
                .getOrCreate();

//        socketStructuredStream(sparkSession);
        kafkaStructuredStream(sparkSession);
    }

    //nc -lk 9999
    public static void socketStructuredStream(SparkSession sparkSession) throws TimeoutException, StreamingQueryException {
        Map<String, String> options = Maps.newHashMap();
        options.put("host", "localhost");
        options.put("port", "9999");
        Dataset<Row> lines = sparkSession.readStream().format("socket").options(options).load();
        Dataset<String> wordDs = lines.as(Encoders.STRING()).flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                return Arrays.stream(s.split(" ")).iterator();
            }
        }, Encoders.STRING());

        Dataset<Row> wordCountDf = wordDs.groupBy("value").count();
        StreamingQuery streamingQuery = wordCountDf.writeStream().outputMode(OutputMode.Complete()).format("console").start();

        streamingQuery.awaitTermination();
    }

    public static void kafkaStructuredStream(SparkSession sparkSession) throws TimeoutException, StreamingQueryException {
        Map<String, String> options = Maps.newHashMap();
        options.put("kafka.bootstrap.servers", "172.16.5.56:9092");
        options.put("subscribe", "Test123");
        options.put("group.id", "kled");
        //读取kafka元数据: [key, offset, partition, timestamp, timestampType, topic, value]
        Dataset<Row> testDs = sparkSession.readStream().format("kafka").options(options).load();

        //读取kafka value json数据，转化成dataframe
        Dataset<String> valueDs = testDs.selectExpr("cast(value as string)").as(Encoders.STRING());
        Encoder<Person> encoder = Encoders.bean(Person.class);
        Dataset<Person> personDataset = valueDs.map(new MapFunction<String, Person>() {
            @Override
            public Person call(String s) throws Exception {
                Gson gson = new Gson();
                return gson.fromJson(s, Person.class);
            }
        }, encoder);

        StreamingQuery personStreamingQuery = personDataset.writeStream().outputMode(OutputMode.Append()).format("console").start();
        personStreamingQuery.awaitTermination();

//        Dataset<Row> rowDataset = testDs.selectExpr("CAST(age AS STRING)").groupBy("key").count();
//        StreamingQuery streamingQuery = rowDataset.writeStream().outputMode(OutputMode.Complete()).format("console").start();
//        streamingQuery.awaitTermination();

    }
}
