package spark.remote;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Kled
 * @date 2021/7/1 11:47 上午
 */
public class SparkCharCount {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .appName("SparkCharCount")
                .master("yarn")
                .getOrCreate();
        JavaRDD<String> lines = spark.read().textFile("/spark/charInput.txt").toJavaRDD();
        JavaRDD<String> charRDD = lines.flatMap(s -> s.chars().mapToObj(i -> (char) i).map(Object::toString).iterator());
        JavaPairRDD<String, Integer> charPairRDD = charRDD.mapToPair(s -> new Tuple2<>(s, 1));
        JavaPairRDD<String, Integer> charCountRDD = charPairRDD.reduceByKey(Integer::sum);
        List<Tuple2<String, Integer>> output = charCountRDD.collect();
        System.out.println("========================SparkCharCount out=============================");
        for (Tuple2<?,?> tuple : output) {
            System.out.println(tuple._1() + ": " + tuple._2());
        }
//        charCountRDD.saveAsTextFile("/spark/charOutput");
        spark.stop();
    }
}
