package java_base;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class testRandom {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(new Random().nextInt(2));
        }

        Integer aInteger = 1;
        Integer bInteger = 1;
        System.out.println(aInteger == bInteger);
        System.out.println(aInteger.equals(bInteger));

        Integer integer = ThreadLocalRandom.current().nextInt(); // 线程随机数
    }

}
