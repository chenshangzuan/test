package java_base.reserve_keyword;

/**
 * @author kled
 * @version $Id: java_base.reserve_keyword.TestFinal.java, v 0.1 2018-08-28 10:18:39 kled Exp $
 */
public class TestFinal {

    public static void main(String[] args)  {
        String a = "hello2";
        final String b = "hello";
        final String bb = getHello();
        String c = "hello";

        String d = b + 2;
        String e = c + 2;
        String f = "hello"+ 2;
        String g = c.intern()+2; // intern() 如果堆内不存在该字符串，将该字符串或者对应的引用存在常量池
        String h = bb + 2;
        String i = a.intern();

        //关键： String在使用+赋值时，只要出现非final类型的字符串变量，会在堆内存中产生变量，而不是常量池中
        System.out.println((a == d));  //true final修饰的变量在编译期会当做常量使用,编译期可确定使用同一常量
        System.out.println((a == h));  //false bb 运行期才确定，所有h引用指向堆内存
        System.out.println((a == e));  //false c 运行期才确定，所有h引用指向堆内存
        System.out.println((a == f));  //true f 编译期确定
        System.out.println((a == g));  //false g 运行期确定
        System.out.println((a == i));  //true i 运行期去常量池查找，并a.intern()返回a的引用
    }

    private static String getHello() {
        return "hello";
    }
}

