package kled.test;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.ExistsBuilder;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * @author: Kled
 * @version: TestZkSemaphore.java, v0.1 2020-12-11 15:32 Kled
 */
public class TestZkIdGenerator extends SpringTestZkSpringBootApplicationTests{

    @Autowired
    private CuratorFramework curatorFramework;

    @Test
    public void testIdGenerator() throws Exception {
        String path = "/idGenerator";

        ExistsBuilder builder = curatorFramework.checkExists();
        Stat stat = builder.forPath(path);
        if (stat == null) {
            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, null);
        }
        for (int i = 0; i < 3; i++) {
            int nextSequenceId = curatorFramework.setData().withVersion(-1).forPath(path, "".getBytes()).getVersion();
            System.out.println(nextSequenceId);
        }
    }
}
