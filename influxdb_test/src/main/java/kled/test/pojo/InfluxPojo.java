package kled.test.pojo;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;
import org.influxdb.annotation.TimeColumn;

import java.time.Instant;

/**
 * @author: Kled
 * @version: InfluxPojo.java, v0.1 2020-12-27 21:36 Kled
 */
@Measurement(name = "test")
public class InfluxPojo {

    @TimeColumn
    @Column(name = "time")
    private Instant time;

    @Column(name = "host", tag = true)
    private String hostname;

    @Column(name = "msg")
    private String msg;

    @Column(name = "count")
    private Integer count;

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "InfluxPojo{" +
                "time=" + time +
                ", hostname='" + hostname + '\'' +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                '}';
    }
}
