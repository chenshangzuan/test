package spark.remote;

import com.google.common.collect.Lists;
import org.apache.spark.sql.*;

import java.util.List;

/**
 * @author Kled
 * @date 2021/7/1 11:47 上午
 */
public class SparkDsTest {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .appName("SparkDsTest")
                .master("yarn")
                .getOrCreate();

        List<Person> personList = Lists.newArrayList(new Person("Kled", 1), new Person("Lee", 2));
        Encoder<Person> encoder = Encoders.bean(Person.class);
        Dataset<Person> personDataset = spark.createDataset(personList, encoder);
        personDataset = personDataset.filter(personDataset.col("name").equalTo("Lee"));
        System.out.println("========================SparkSQLTest person out=============================");
        personDataset.show();
        for (String column : personDataset.columns()) {
            System.out.println(column);
        }

        System.out.println("========================SparkSQLTest json out=============================");
        Dataset<String> jsonStringDs = spark.createDataset(Lists.newArrayList("{\"name\": \"1232121\", \"age\": 12}"), Encoders.STRING());
        Dataset<Row> jsonDs = spark.read().json(jsonStringDs);
        jsonDs.show();
        spark.stop();
    }
}
