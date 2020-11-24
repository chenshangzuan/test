package kled.test;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import kled.test.entity.User;
import kled.test.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.CountDownLatch;

/**
 * @author: Kled
 * @version: MysqlMasterSlaveTest.java, v0.1 2020-10-04 20:22 Kled
 */
public class MysqlMasterSlaveTest extends kled.test.SpringTestISpringBootApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    public void test0() {
        for (int i = 0; i < 5; i++) {
            System.out.println(userService.getById(1));
        }
    }

    @Test
    public void test1() {
        for (int i = 0; i < 3; i++) {
            User user1 = new User();
            user1.setName("shardingsphere_kled" + i);
            userService.saveOrUpdate(user1);
            System.out.println(user1.getId());
        }
    }

    @Test
    public void test2() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            User user1 = new User();
            user1.setName("shardingsphere_x");
            userService.saveOrUpdate(user1);
            System.out.println("create one user, tsms=" + System.currentTimeMillis());
        }).start();

        new Thread(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (true) {
                User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getName, "shardingsphere_x"));
                if (user != null) {
                    System.out.println("query one user, tsms=" + System.currentTimeMillis());
                    break;
                } else {
                    System.out.println("user is not found, tsms=" + System.currentTimeMillis());
                }
            }
        }).start();

        countDownLatch.countDown();
        Thread.sleep(5000);

        userService.remove(Wrappers.<User>lambdaQuery().eq(User::getName, "shardingsphere_x"));
    }

    @Test
    public void test3() throws InterruptedException {
        //考虑读写操作分离后，数据同步时间对
        User user1 = new User();
        user1.setName("shardingsphere_x");
        userService.saveOrUpdate(user1);
        System.out.println("create one user, tsms=" + System.currentTimeMillis());

        while (true){
            User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getName, "shardingsphere_x"));
            if (user != null) {
                System.out.println("query one user, tsms=" + System.currentTimeMillis());
                break;
            } else {
                System.out.println("user is not found, tsms=" + System.currentTimeMillis());
            }
        }

        userService.remove(Wrappers.<User>lambdaQuery().eq(User::getName, "shardingsphere_x"));
    }

    @Test
    public void test4() {
        transactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus transactionStatus) {
                try {
                    //事务中的读写都是在Master上
                    test3();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    @Test
    public void test5() throws InterruptedException {
        for (int i = 0; i < 2; i++) {
            System.out.println(userService.getById(1));
        }

        //考虑读写操作分离后，数据同步时间对
        User user1 = new User();
        user1.setName("shardingsphere_x");
        userService.saveOrUpdate(user1);

        for (int i = 0; i < 2; i++) {
            System.out.println(userService.getById(1));
        }

        userService.remove(Wrappers.<User>lambdaQuery().eq(User::getName, "shardingsphere_x"));
    }
}
