package kled.test;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: Kled
 * @version: TestZkLeaderLatch.java, v0.1 2020-12-01 16:21 Kled
 */
public class TestZkLeaderLatch extends SpringTestZkSpringBootApplicationTests {

    @Autowired
    private CuratorFramework curatorFramework;

    @Test
    public void testLeaderLatch() throws InterruptedException {
        String PATH = "/leader";
        LeaderLatch leaderLatch = new LeaderLatch(curatorFramework, PATH, "Client#1");
        leaderLatch.addListener(new LeaderLatchListener() {
            @Override
            public void isLeader() {
                System.out.println("Currently run as leader");
            }

            @Override
            public void notLeader() {
                System.out.println("Currently run as slave");
            }
        });
        try {
            leaderLatch.start();
            Thread.sleep(1000);
            leaderLatch.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLeaderLatchSwitch() throws InterruptedException {
        String PATH = "/leader";
        Thread thread1 = new Thread(() -> {
            LeaderLatch leaderLatch = new LeaderLatch(curatorFramework, PATH, "Client#1");
            leaderLatch.addListener(new LeaderLatchListener() {
                @Override
                public void isLeader() {
                    System.out.println("Client#1 run as leader");
                }

                @Override
                public void notLeader() {
                    System.out.println("Client#1 run as slave");
                }
            });
            try {
                leaderLatch.start();
                Thread.sleep(1000);
                leaderLatch.close();
                System.out.println("thread1 is leader latch is closed");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            LeaderLatch leaderLatch = new LeaderLatch(curatorFramework, PATH, "Client#2");
            leaderLatch.addListener(new LeaderLatchListener() {
                @Override
                public void isLeader() {
                    System.out.println("Client#2 run as leader");
                }

                @Override
                public void notLeader() {
                    System.out.println("Client#2 run as slave");
                }
            });
            try {
                leaderLatch.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread2 is leader=" + leaderLatch.hasLeadership());
            }
        });
        thread2.start();
        Thread.sleep(10000);
    }
}
