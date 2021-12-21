package spark.local;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.storage.StorageLevel;
import scala.Tuple2;
import spark.remote.Person;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kled
 * @date 2021/7/1 11:47 上午
 */
public class SparkLocalDsTest {
    @SneakyThrows
    public static void main(String[] args) throws AnalysisException {
        SparkSession sparkSession = SparkSession
                .builder()
//                .config() //spark config
                .appName("SparkLocalTest")
                .master("local")
                .getOrCreate();

        personDs(sparkSession);

//        personDsUdfExpr(sparkSession);

//        personDsJoin(sparkSession);

//        personDsGroup(sparkSession);

//        IntegerRangeDs(sparkSession);

    }

    private static void personDsUdfExpr(SparkSession sparkSession) {
        //注册UDF函数
        sparkSession.udf().register("myUpper", new UDF1<String, String>() {
            @Override
            public String call(String s) throws Exception {
                return s.toUpperCase();
            }
        }, DataTypes.StringType);

        List<Person> personList1 = Lists.newArrayList(new Person("Kled", 1), new Person("Lee", 2), new Person("Coco", 3));
        Encoder<Person> encoder = Encoders.bean(Person.class);
        Dataset<Person> personDataset1 = sparkSession.createDataset(personList1, encoder);
        personDataset1.selectExpr("myUpper(name)").show();
        //+-------------+
        //|myUpper(name)|
        //+-------------+
        //|         KLED|
        //|          LEE|
        //|         COCO|
        //+-------------+
    }

    private static void personDsGroup(SparkSession sparkSession) {
        List<Person> personList1 = Lists.newArrayList(new Person("Kled", 1), new Person("Lee", 2), new Person("Coco", 3), new Person("Lee", 3));
        Encoder<Person> encoder = Encoders.bean(Person.class);
        Dataset<Person> personDataset1 = sparkSession.createDataset(personList1, encoder);
        RelationalGroupedDataset relationalGroupedDataset = personDataset1.groupBy("name");
        relationalGroupedDataset.count().show();
        //+----+-----+
        //|name|count|
        //+----+-----+
        //|Coco|    1|
        //|Kled|    1|
        //| Lee|    2|
        //+----+-----+

        relationalGroupedDataset.agg(ImmutableMap.of("age", "max")).show();
        //+----+--------+
        //|name|max(age)|
        //+----+--------+
        //|Coco|       3|
        //|Kled|       1|
        //| Lee|       3|
        //+----+--------+
    }

    private static void personDsJoin(SparkSession sparkSession) {
        List<Person> personList1 = Lists.newArrayList(new Person("Kled", 1), new Person("Lee", 2), new Person("Coco", 3));

        List<Person> personList2 = Lists.newArrayList(new Person("Sum", 1), new Person("Coco", 2));

        Encoder<Person> encoder = Encoders.bean(Person.class);

        Dataset<Person> personDataset1 = sparkSession.createDataset(personList1, encoder);
        Dataset<Person> personDataset2 = sparkSession.createDataset(personList2, encoder);

//        sparkSession.createDataFrame(personList1, Person.class);

        //内连接
        Dataset<Row> innerJoinDf = personDataset1.join(personDataset2, personDataset1.col("age").equalTo(personDataset2.col("age")), "inner");
//        Dataset<Row> innerJoinDf = personDataset1.join(personDataset2, "age");
        innerJoinDf.show();
        //+---+----+---+----+
        //|age|name|age|name|
        //+---+----+---+----+
        //|  1|Kled|  1| Sum|
        //|  2| Lee|  2|Coco|
        //+---+----+---+----+

        //左连接
        Dataset<Row> leftJoinDf = personDataset1.join(personDataset2, personDataset1.col("name").equalTo(personDataset2.col("name")), "left");
        leftJoinDf.select(personDataset1.col("age").alias("ds1.age"), personDataset1.col("name").alias("ds1.name"),
                personDataset2.col("age").alias("ds2.age"), personDataset2.col("name").alias("ds2.name")).show();
        //+-------+--------+-------+--------+
        //|ds1.age|ds1.name|ds2.age|ds2.name|
        //+-------+--------+-------+--------+
        //|      1|    Kled|   null|    null|
        //|      2|     Lee|   null|    null|
        //|      3|    Coco|      2|    Coco|
        //+-------+--------+-------+--------+

        //笛卡尔积
        Dataset<Row> crossJoin = personDataset1.crossJoin(personDataset2);
        crossJoin.show();
        //+---+----+---+----+
        //|age|name|age|name|
        //+---+----+---+----+
        //|  1|Kled|  1| Sum|
        //|  1|Kled|  2|Coco|
        //|  2| Lee|  1| Sum|
        //|  2| Lee|  2|Coco|
        //|  3|Coco|  1| Sum|
        //|  3|Coco|  2|Coco|
        //+---+----+---+----+

    }

    private static void personDs(SparkSession sparkSession) throws AnalysisException, IOException {
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

    private static void IntegerRangeDs(SparkSession sparkSession) {
        System.out.println("========================SparkLocalTest range ds out=============================");
        Dataset<Long> integerDataset = sparkSession.range(1, 10, 2);
        integerDataset.show();
    }
}
