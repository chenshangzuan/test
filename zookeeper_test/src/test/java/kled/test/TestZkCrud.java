package kled.test;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.ExistsBuilder;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: Kled
 * @version: TestZkSemaphore.java, v0.1 2020-12-11 15:32 Kled
 */
public class TestZkCrud extends SpringTestZkSpringBootApplicationTests{

    @Autowired
    private CuratorFramework curatorFramework;

    @Test
    public void testCrud() throws Exception {
        String path = "/node";

        ExistsBuilder builder = curatorFramework.checkExists();
        Stat stat = builder.forPath(path);
        if (stat == null) {
            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, "kled node".getBytes());
            stat = builder.forPath(path);
        }

        System.out.println("old version = " + stat.getVersion());
        Stat myStat1 = curatorFramework.setData().withVersion(stat.getVersion()).forPath(path, "".getBytes());
        System.out.println("new version = " + myStat1.getVersion());

        try {
            Stat myStat2 = curatorFramework.setData().withVersion(1000).forPath(path, "".getBytes());
        } catch (Exception e) {
            System.out.println("set node data error, due to the error data version");
        }

        //忽略数据版本进行更新
        Stat myStat3 = curatorFramework.setData().withVersion(-1).forPath(path, "".getBytes());
        System.out.println("ignore old version update and now version = " + myStat3.getVersion());

        curatorFramework.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
    }
}
