package kled.test;

import kled.test.pojo.InfluxPojo;
import org.assertj.core.util.Lists;
import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.influxdb.querybuilder.BuiltQuery;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: Kled
 * @version: InfluxdbTest.java, v0.1 2020-12-27 11:08 Kled
 */
public class InfluxdbTest extends SpringTestInfluxdbTestSpringBootApplicationTests {

    @Autowired
    private InfluxDB influxDB;

    private static final String DATABASE = "kled_test";

    private InfluxDBResultMapper influxDBResultMapper = new InfluxDBResultMapper();

    @Test
    public void test1(){
        System.out.println(influxDB.query(new Query("CREATE DATABASE " + DATABASE)));
    }

    @Test
    public void test2() throws InterruptedException {
        influxDB.setDatabase(DATABASE);

        //过期策略必须配置
        String retentionPolicyName = "one_day_only";
        influxDB.query(new Query("CREATE RETENTION POLICY " + retentionPolicyName
                + " ON " + DATABASE + " DURATION 1d REPLICATION 1 DEFAULT"));
        influxDB.setRetentionPolicy(retentionPolicyName);

        influxDB.enableBatch(BatchOptions.DEFAULTS);

        BatchPoints batchPoints = BatchPoints
                .database(DATABASE)
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();
        batchPoints.point(Point.measurement("h2o_feet")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("location", "santa_monica")
                .addField("level description", "below 3 feet")
                .addField("water_level", 2.064d)
                .build());
        batchPoints.point(Point.measurement("h2o_feet")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("location", "coyote_creek")
                .addField("level description", "between 6 and 9 feet")
                .addField("water_level", 8.12d)
                .build());
        influxDB.write(batchPoints);

        Thread.sleep(2_000L);

        QueryResult queryResult = influxDB.query(new Query("SELECT * FROM h2o_feet"));
        System.out.println(queryResult);

        influxDB.close();
    }

    @Test
    public void testInfluxPojo() throws InterruptedException {
        InfluxPojo influxPojo = new InfluxPojo();
        influxPojo.setTime(Instant.now());
        influxPojo.setHostname("localhost");
        influxPojo.setMsg("test influx pojo");
        influxPojo.setCount(1);

        influxDB.setDatabase(DATABASE);

        String retentionPolicyName = "one_day_only";
        influxDB.query(new Query("CREATE RETENTION POLICY " + retentionPolicyName
                + " ON " + DATABASE + " DURATION 1d REPLICATION 1 DEFAULT"));
        influxDB.setRetentionPolicy(retentionPolicyName);

        Point point = Point.measurementByPOJO(InfluxPojo.class).addFieldsFromPOJO(influxPojo).build();
        influxDB.write(point);
        Thread.sleep(2_000L);

        QueryResult queryResult = influxDB.query(new Query("SELECT * FROM test"));
        List<InfluxPojo> pojos = influxDBResultMapper.toPOJO(queryResult, InfluxPojo.class);
        System.out.println(pojos);
    }
}
