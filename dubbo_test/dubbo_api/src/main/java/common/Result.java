package common;

/**
 * @author: Kled
 * @version: Result.java, v0.1 2020-11-24 22:00 Kled
 */
public class Result<T> {

    private boolean success;

    private T data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", data=" + data +
                '}';
    }
}
