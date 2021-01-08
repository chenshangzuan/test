package kled.test.config;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Kled
 * @version: InfluxdbConfig.java, v0.1 2020-12-27 20:39 Kled
 */
@Configuration
public class InfluxdbConfig {

      //InfluxDbAutoConfiguration自动装配InfluxDB

//    @Value("${spring.influx.url}")
//    private String influxDBUrl;
//
//    @Bean
//    public InfluxDB myInfluxDb(){
//        return InfluxDBFactory.connect(influxDBUrl);
//    }
}
