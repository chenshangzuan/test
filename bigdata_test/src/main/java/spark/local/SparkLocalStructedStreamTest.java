package spark.local;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.AnalysisException;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author Kled
 * @date 2021/7/1 11:47 上午
 */
public class SparkLocalStructedStreamTest {
    public static void main(String[] args) throws AnalysisException, InterruptedException {
//        SparkConf conf = new SparkConf().setAppName("SparkLocalStreamTest").setMaster("local[2]");
//        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(1));
    }
}
