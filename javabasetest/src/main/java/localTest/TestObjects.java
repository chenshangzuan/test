package localTest;

import com.google.common.base.Objects;

/**
 * @author chenpc
 * @version $Id: localTest.TestObjects.java, v 0.1 2018-05-25 23:05:29 chenpc Exp $
 */
public class TestObjects {

    public static void main(String[] args) {
        System.out.println(Objects.equal(null,null)); //

        Integer a = 100;
        Integer b = 100;
        System.out.println(a == b); // true 在-128 至 127 范围内的赋值，Integer 对象是在 IntegerCache.cache 产生，会复用已有对象

        Integer c = 200;
        Integer d = 200;
        System.out.println(c == d); //false

        //hashCode和euqal 保持一致。   集合类Set/HashMap依据hashCode和equals进行判断
    }
}
