package localTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * @author chenpc
 * @version $Id: localTest.TestReflect.java, v 0.1 2018-05-07 17:12:31 chenpc Exp $
 */
public class TestReflect {
    public static void main(String[] args) throws Exception{
        Example example = new Example();
        Class clazz1 = Example.class;
        Constructor constructor = clazz1.getConstructor(null);
        example = (Example) constructor.newInstance(null);
        //Class clazz2 = Class.forName("localTest.Example");
        //Class clazz3 = example.getClass();

        Field[] fields = clazz1.getDeclaredFields(); //获取所有申明的属性
        //Field[] fields = clazz.getFields(); //获取public的属性
        for (Field f: fields) {
            System.out.println(f.getName());
            Test testAnotation = f.getAnnotation(Test.class);
            if(testAnotation !=null){
                System.out.println(testAnotation.hello());
            }
        }
    }
}
class Example{

    @Test(hello = "hello world")
    public String s1;

    private String s2;

    private String s3;

}
