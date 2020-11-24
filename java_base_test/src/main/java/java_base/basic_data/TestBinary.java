package java_base.basic_data;

/**
 * @author chenpc
 * @version $Id: java_base.basic_data.TestBinary.java, v 0.1 2018-04-25 15:24:41 chenpc Exp $
 */
public class TestBinary {
    public static void main(String[] args) {
        int a = 1;
        int b = 1;
        int c = 1;
        int d = a + (b<<1)+ (c<<2);
        System.out.println(d);
    }
}
