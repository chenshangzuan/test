package java_base.juc;

import sun.misc.Unsafe;

/**
 * @author Kled
 * @date 2022/4/29 11:59 AM
 */
public class TestUnsafe {

    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();
        //unsafe.park();
        //unsafe.unpark();
    }
}
