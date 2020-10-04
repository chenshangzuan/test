package localTest;

import com.google.common.base.CaseFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class TestSubString {

    public static void main(String[] args) throws ParseException {

        List<String> s = Arrays.asList("test");
        System.out.println(s.contains("test"));

        StringBuffer stringBuffe =  new StringBuffer();
        System.out.println(stringBuffe.length());

        String ss  = "com.cdasd.casda";
        System.out.println(ss.indexOf(46, 0));

        //        StringBuffer sBuffer = new StringBuffer();
        //        sBuffer.append("172.16.0.212:6377,172.16.0.215:6377,172.16.0.216:6377,");
        //        System.out.print(sBuffer.substring(0,sBuffer.length()-1));

        //System.out.println(StringUtils.substringBetween("10GE1/1/11/GE", "GE", "/"));
        //
        //Character[] c = new Character[2];
        //c[0] = 'a';
        //c[1] = 'b';
        //System.out.println(c.toString());
        //System.out.println(String.valueOf(c)); //调用了String.valueOf(Object o)方法
        //重载方法在编译阶段决定调用哪个重载方法
        //覆写方法在运行时根据环境决定调用哪个覆写方法

        String a = "aaa";
        String b = new String("aaa").intern(); //从常量池中先查找是否存在该字符串

        System.out.println(a == b);

        System.out.println(System.currentTimeMillis());

        System.out.println(new SimpleDateFormat("yyyy-MM-dd").parse("2011-11-11"));

        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, "cpeHa"));

        System.out.println(new Integer(100).equals(null));

        System.out.println(new String("5fe104d7-87b4-481f-a370-ec54bbb50a8b").toUpperCase());


    }

}
