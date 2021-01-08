package java_base.openFeign;

/**
 * @author: Kled
 * @version: QueryRequest.java, v0.1 2020-12-28 14:58 Kled
 */
public class QueryRequest {

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "QueryRequest{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
