package spark.local;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.storage.StorageLevel;
import scala.Tuple2;
import spark.remote.Person;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kled
 * @date 2021/7/1 11:47 上午
 */
public class SparkLocalDsTest {
    @lombok.SneakyThrows
    public static void main(String[] args) throws AnalysisException {
        SparkSession sparkSession = SparkSession
                .builder()
                .appName("SparkLocalTest")
                .master("local")
                .getOrCreate();

        System.out.println("========================SparkLocalTest range ds out=============================");
        Dataset<Long> integerDataset = sparkSession.range(1,10,2);
        integerDataset.show();

        System.out.println("========================SparkLocalTest person ds out=============================");
        StructType structType = DataTypes.createStructType(Lists.newArrayList(DataTypes.createStructField("name", DataTypes.StringType, true),
                DataTypes.createStructField("age", DataTypes.IntegerType, true)));
        //json文件不能格式化，只能整行
        Encoder<Person> encoder = Encoders.bean(Person.class);
        Dataset<Row> personDf = sparkSession.read().schema(structType).json("/Users/kled/git/test/bigdata_test/src/main/resources/people.json");
        Dataset<Person> personDs = personDf.as(encoder);
        personDs.printSchema();
        personDs.count();
        personDs.persist(StorageLevel.MEMORY_ONLY());
        personDs.cache(); //数据集缓存至内存
        personDs.describe("name"); //计算指定类的统计信息，如count、mean、stddev、min及max
//        personDs.checkpoint(); //设置检查点，存在checkpoint目录, 需要在sparkContext中设置
        personDs.show();
        personDs.explain(); //返回物理执行计划

        personDs = personDs.map((MapFunction<Person, Person>) person -> {
            person.setAge(person.getAge() + 10);
            return person;
        }, encoder).filter((FilterFunction<Person>) person -> person.getAge() >= 20)
                .where("age >= 20")/*.join().group().agg()*/;

        List<Person> personList = personDs.collectAsList();
        System.out.println("personList:" + personList);
        String[] personDsColumns = personDs.columns();
        System.out.println("personDsColumns:" + String.join("", personDsColumns));
        Tuple2<String, String>[] personDsTypes = personDs.dtypes();
        System.out.println("personDsTypes:" + Arrays.stream(personDsTypes).map(Object::toString).collect(Collectors.joining()));

        System.out.println("========================SparkLocalTest person drop age out=============================");
        personDs.drop("age").show();

        System.out.println("========================SparkLocalTest person view & sql out=============================");
        //局部视图
        personDs.createOrReplaceTempView("tmp_view_1");
        sparkSession.sql("select * from tmp_view_1").show();

        //全局视图
        personDs.createGlobalTempView("tmp_view_2");
        sparkSession.sql("select * from global_temp.tmp_view_2").show();

        System.out.println("========================SparkLocalTest person write csv & json=============================");
        String peopleCsvOutputPath = "/Users/kled/git/test/bigdata_test/src/main/resources/PeopleCsvOutput";
        String peopleJsonOutputPath = "/Users/kled/git/test/bigdata_test/src/main/resources/PeopleJsonOutput";
        File peopleCsvOutputFile = new File(peopleCsvOutputPath);
        File peopleJsonOutputFile = new File(peopleJsonOutputPath);
        FileUtils.deleteDirectory(peopleCsvOutputFile);
        FileUtils.deleteDirectory(peopleJsonOutputFile);
        personDs.write().csv(peopleCsvOutputPath);
        personDs.write().json(peopleJsonOutputPath);
    }
}
