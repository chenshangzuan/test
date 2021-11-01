package spark.local;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.spark.Partition;
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
        //采样
        System.out.println(stringJavaRDD1.sample(false, 0.1).countByValue());
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

        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        //设定2个分区
        JavaRDD<Integer> stringJavaRDD4 = sparkContext.parallelize(integers, 2);
        //stringJavaRDD4.repartition(3); 重新分区
        //如果你减少分区数，考虑使用coalesce，这样可以避免执行shuffle。但是假如内存不够用，可能会引起内存溢出。
        //stringJavaRDD4.coalesce(2, true); //第一个参数为重分区的数目，第二个为是否进行shuffle(分区增加必须shuffle)，默认为false;
        //按分区进行map，可遍历(查看)各分区的数据，
        stringJavaRDD4.mapPartitionsWithIndex((index, iterator) -> {
            List<Integer> partitionIntegers = Lists.newArrayList(iterator);
            System.out.println("Partition Index=" + index + ", data=" + partitionIntegers);
            return iterator;
        }, false).collect();
        //汇聚(前后值依次运算)
        Integer reduceRet = stringJavaRDD4.reduce(Integer::sum);
        //汇聚(分区汇聚和最终的汇聚函数相同) + 初始值
        Integer foldRet = stringJavaRDD4.fold(0, Integer::sum);
        //汇聚(seqOp:分区聚合+func:最终的聚合) + 初始值
        Integer aggregateRet = stringJavaRDD4.aggregate(1, (x, y) -> x * y , Integer::sum);
        System.out.println("foldRet=" + foldRet + ", reduceRet=" + reduceRet + ", aggregateRet=" + aggregateRet);

        //分组
        JavaPairRDD<String, Iterable<Integer>> iterableJavaPairRDD = stringJavaRDD4.groupBy(i -> i < 4 ? "LT-4" : "GE-4");
        //支持按Key进行汇聚
        //iterableJavaPairRDD.aggregateByKey()
        //iterableJavaPairRDD.reduceByKey()
        //iterableJavaPairRDD.foldByKey()
        List<Tuple2<String, Iterable<Integer>>> tuple2List = iterableJavaPairRDD.collect();
        for (Tuple2<String, Iterable<Integer>> stringIterableTuple2 : tuple2List) {
            System.out.println("GroupBy key=" + stringIterableTuple2._1 + ", value=" + Lists.newArrayList(stringIterableTuple2._2));
        }
    }
}
