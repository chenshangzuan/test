package localTest;

import java.util.Timer;
import java.util.TimerTask;

public class TestTimer {

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                System.out.println("first timer task :" + System.currentTimeMillis());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
            }

        }, 0, 5000);
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                System.out.println("second timer task " + System.currentTimeMillis());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
            }

        }, 0, 5000);
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                System.out.println("third timer task " + System.currentTimeMillis());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
            }

        }, 0, 5000);
        //Timer串行处理schedule，队列存储
    }

}
