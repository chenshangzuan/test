package java_base;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author Kled
 * @date 2021/9/29 10:10 上午
 */
public class TestObjectLayout {
    public static void main(String[] args) {
        //jdk1.8默认开启压缩
        //-XX:-UseCompressedOops -XX:-UseCompressedClassPointers 不启用指针压缩
        int[] ints = new int[]{1, 2, 4};
        System.out.println(ClassLayout.parseInstance(ints).toPrintable());

//        [I object internals:
//        OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
//        0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)         //Mark Word
//        4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
//        8     4        (object header)                           68 0b 20 26 (01101000 00001011 00100000 00100110) (639634280) //类元数据指针class pointer
//        12     4        (object header)                           01 00 00 00 (00000010 00000000 00000000 00000000) (2)
//        16     4        (object header)                           03 00 00 00 (00000011 00000000 00000000 00000000) (3)        //数组长度
//        20     4        (alignment/padding gap)                        //数据间隙填充
//        24    12    int [I.<elements>                             N/A  //实例数
//        36     4        (loss due to the next object alignment)        //对象补齐 pending，保证实例大小为8B的倍数
//        Instance size: 40 bytes
//        Space losses: 4 bytes internal + 4 bytes external = 8 bytes total

        String str = "hello world";
        System.out.println(ClassLayout.parseInstance(str).toPrintable());

        Integer integer = 213;
        System.out.println(ClassLayout.parseInstance(integer).toPrintable());
    }
}
