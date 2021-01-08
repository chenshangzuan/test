package java_base.openFeign;

import feign.RetryableException;
import feign.Retryer;

/**
 * @author: Kled
 * @version: MyRetryer.java, v0.1 2020-12-28 14:47 Kled
 */
public class MyRetryer implements Retryer {
    @Override
    public void continueOrPropagate(RetryableException e) {

    }

    @Override
    public Retryer clone() {
        return null;
    }
}
