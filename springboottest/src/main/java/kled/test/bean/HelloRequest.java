package kled.test.bean;

/**
 * @author: Kled
 * @version: HelloRequest.java, v0.1 2020-11-12 14:07 Kled
 */
public class HelloRequest {

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "HelloRequest{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
