package java_base.basic_data;

import java.util.stream.Stream;

/**
 * @author kled
 * @version $Id: java_base.basic_data.TestByte.java, v 0.1 2018-07-12 11:43:39 kled Exp $
 */
public class TestByte {
    public static void main(String[] args) {
        String s = "陈 test";
        byte[] bytes = s.getBytes();  //UTF-8编码值 A~Z 65~90 中文3字节 英文字母及符号1字节
        for (int i = 0; i < bytes.length; i++) {
            System.out.println(bytes[i]);
        }
        Stream.of(bytes).forEach(System.out::println);
    }
}
