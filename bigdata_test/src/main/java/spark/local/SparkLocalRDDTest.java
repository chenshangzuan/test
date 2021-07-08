package spark.local;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.util.CollectionAccumulator;
import org.apache.spark.util.DoubleAccumulator;
import org.apache.spark.util.LongAccumulator;
import scala.Tuple2;
import scala.collection.JavaConverters;
import scala.collection.Seq;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kled
 * @date 2021/7/1 11:47 上午
 */
public class SparkLocalRDDTest {
    public static void main(String[] args) throws AnalysisException {
        SparkConf conf = new SparkConf().setAppName("spark test1").setMaster("local[2]");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);
        //累加器
        LongAccumulator longAccumulator = sparkContext.sc().longAccumulator();
        DoubleAccumulator doubleAccumulator = sparkContext.sc().doubleAccumulator();
        CollectionAccumulator<Integer> collectionAccumulator = sparkContext.sc().collectionAccumulator();

        //广播变量
        Broadcast<String> blackName = sparkContext.broadcast("Lee");

        System.out.println("========================SparkLocalTest RDD out=============================");
        List<String> strings = Arrays.asList("a", "b", "c");
        JavaRDD<String> stringJavaRDD1 = sparkContext.parallelize(strings);
        stringJavaRDD1 = stringJavaRDD1.map(str -> {
            longAccumulator.add(1);
            return str;
        });
        System.out.println(stringJavaRDD1.collect());
        System.out.println(longAccumulator.count());

        JavaRDD<String> stringJavaRDD2 = sparkContext.textFile("/Users/kled/git/test/bigdata_test/src/main/resources/rdd.txt", 1);
        stringJavaRDD2 = stringJavaRDD2.flatMap(str -> Arrays.stream(str.split(" ")).iterator());
        System.out.println(stringJavaRDD2.collect());

        JavaRDD<String> stringJavaRDD3 = sparkContext.parallelize(Arrays.asList("Kled Chen", "Lee Li"));
        JavaPairRDD<String, String> stringStringJavaPairRDD3 = stringJavaRDD3.mapToPair(str -> new Tuple2<>(str.split(" ")[0], str.split(" ")[1]));
        //过滤黑名单成员
        stringStringJavaPairRDD3 = stringStringJavaPairRDD3.filter(tuple -> !tuple._1().equals(blackName.getValue()));
        System.out.println(stringStringJavaPairRDD3.collect());
    }
}
