package spark.local;

import com.google.common.collect.Lists;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import spark.remote.Person;

/**
 * @author Kled
 * @date 2021/10/18 11:31 上午
 */
public class SparkLocalSqlTest {

    public static void main(String[] args) throws AnalysisException {
        SparkSession sparkSession = SparkSession
                .builder()
                .appName("SparkLocalTest")
                .master("local")
                .getOrCreate();

        StructType structType = DataTypes.createStructType(Lists.newArrayList(DataTypes.createStructField("name", DataTypes.StringType, true),
                DataTypes.createStructField("age", DataTypes.IntegerType, true)));
        //json文件不能格式化，只能整行
        Encoder<Person> encoder = Encoders.bean(Person.class);
        Dataset<Row> personDf = sparkSession.read().schema(structType).json("/Users/kled/git/test/bigdata_test/src/main/resources/people.json");
        Dataset<Person> personDs = personDf.as(encoder);

        System.out.println("========================SparkLocalTest person view & sql out=============================");
        //局部视图
        //先将ds转化为table或view
        personDs.createOrReplaceTempView("tmp_view_1");
        sparkSession.sql("select * from tmp_view_1").show();

        //注册表才能缓存数据
        personDs.registerTempTable("table_1");
        sparkSession.sqlContext().cacheTable("table_1");
        sparkSession.sqlContext().clearCache();

        //全局视图
        personDs.createGlobalTempView("tmp_view_2");
        sparkSession.sql("select * from global_temp.tmp_view_2").show();

        sparkSession.stop();
    }
}
