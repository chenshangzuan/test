package spark.remote;

import com.google.common.collect.Lists;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.StructType;
import scala.collection.Seq;
import scala.reflect.ClassTag;

/**
 * @author Kled
 * @date 2021/7/1 11:47 上午
 */
public class SparkSQLTest {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .appName("SparkSQLTest")
                .master("yarn")
                .enableHiveSupport()
                .getOrCreate();
        Dataset<Row> ds = spark.sql("select * from user_click");
        System.out.println("========================SparkSQLTest out=============================");
        ds.show();
//        ds.foreach(row -> {
//            System.out.println("userId=" + row.get(0));
//        });
        spark.stop();
    }
}
