package hadoop.mapreduce.Flow.flowPartition;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Kled
 * @date 2021/8/1 11:08 上午
 */
public class FlowInfoBean implements Writable {
    private long ts;
    private String id;
    private String ip;
    private String url;
    private String urlType;
    private String status;
    private int upFlow;
    private int downFlow;
    private int upCountFlow;
    private int downCountFlow;

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(int upFlow) {
        this.upFlow = upFlow;
    }

    public int getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(int downFlow) {
        this.downFlow = downFlow;
    }

    public int getUpCountFlow() {
        return upCountFlow;
    }

    public void setUpCountFlow(int upCountFlow) {
        this.upCountFlow = upCountFlow;
    }

    public int getDownCountFlow() {
        return downCountFlow;
    }

    public void setDownCountFlow(int downCountFlow) {
        this.downCountFlow = downCountFlow;
    }

    @Override
    public String toString() {
        return "\t" + ts +
                "\t" + id +
                "\t" + ip +
                "\t" + url +
                "\t" + urlType +
                "\t" + status +
                "\t" + upFlow +
                "\t" + downFlow +
                "\t" + upCountFlow +
                "\t" + downCountFlow;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(ts);
        out.writeUTF(id);
        out.writeUTF(ip);
        out.writeUTF(url);
        out.writeUTF(urlType);
        out.writeUTF(status);
        out.writeInt(upFlow);
        out.writeInt(downFlow);
        out.writeInt(upCountFlow);
        out.writeInt(downCountFlow);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.ts = in.readLong();
        this.id = in.readUTF();
        this.ip = in.readUTF();
        this.url = in.readUTF();
        this.urlType = in.readUTF();
        this.status = in.readUTF();
        this.upFlow = in.readInt();
        this.downFlow = in.readInt();
        this.upCountFlow = in.readInt();
        this.downCountFlow = in.readInt();
    }
}
