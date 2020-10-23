package java_base;

import java.util.BitSet;

/**
 * @author: Kled
 * @version: TestBitSet.java, v0.1 2020-10-19 11:58 Kled
 */
public class TestBitSet {
    //数字的存在性用位来标识(0,1 <==> false,true)
    //BigSet内部用long[]数据记录所有的数字，一个long64位，可标识64个数字的存在性
    //比如long[0]代表 数字0-63的存在性, long[1]代表数字64-127的存在性

    public static void main(String[] args) {
        BitSet bitSet = new BitSet();
        bitSet.set(61);
        System.out.println(bitSet.cardinality());
        System.out.println(bitSet.get(61));
        System.out.println("bit set size=" + bitSet.size());

        bitSet.clear(61);
        System.out.println(bitSet.get(61));

        //触发long[] words数组扩容
        bitSet.set(120);
        System.out.println("bit set size=" + bitSet.size());

        bitSet.clear(120);
        System.out.println("bit set size=" + bitSet.size());
    }
}
