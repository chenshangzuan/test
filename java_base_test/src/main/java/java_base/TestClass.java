package java_base;


import org.springframework.util.ReflectionUtils;

/**
 * @author: Kled
 * @version: TestClass.java, v0.1 2020-11-24 10:14 Kled
 */
public class TestClass {

    public static void main(String[] args) throws ClassNotFoundException {

        System.out.println(TestClass.class.getGenericSuperclass());
        //System.out.println(new Test().getClass().getName());
        Class.forName("java_base.TestClass$Test", true, ClassLoader.getSystemClassLoader()); //会调用静态代码块进行初始化
        Class.forName("java_base.TestClass$Test", false, ClassLoader.getSystemClassLoader());
    }

    public static class Test{
        static {
            System.out.println("init Test");
        }
    }
}
