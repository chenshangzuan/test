package localTest;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author kled
 * @version $Id: TestCache.java, v 0.1 2018-12-26 10:58:29 kled Exp $
 */
public class TestCache {

    private static final Cache<String, Integer> vapRetryCounter = CacheBuilder.newBuilder()
            .expireAfterAccess(20, TimeUnit.SECONDS)
            .maximumSize(500)
            .build();

    private static final Cache<String, Integer> vapFailures = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)
            .maximumSize(500)
            .build();

    private static String vap = "vapGroup-cpe-wan-1";

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 10; i++) {
            Integer vapFailure = vapFailures.getIfPresent(vap);
            if(vapFailure != null){
                System.out.println("no need to create vap");
                continue;
            }
            Integer vapFailureScore = vapRetryCounter.getIfPresent(vap);
            if(vapFailureScore == null){
                vapRetryCounter.put(vap, 1);
                System.out.println("put vap create failure");
            }else if(vapFailureScore < 6){
                vapRetryCounter.put(vap, ++vapFailureScore);
            }else {
                vapFailures.put(vap, 1);
                System.out.println("report alarm for vap create failure");
            }
            Thread.sleep(1000);
        }
    }
}
