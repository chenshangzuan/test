
package java_base.juc.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author Kled
 * @date 2022/4/29 2:50 PM
 */
public class TestAtomicStampedReference {
    public static void main(String[] args) {
        AtomicStampedReference<Integer> integerAtomicStampedReference = new AtomicStampedReference<>(1, 0);
    }
}
