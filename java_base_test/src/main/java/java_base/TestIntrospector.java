package java_base;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: Kled
 * @version: TestIntrospector.java, v0.1 2020-11-19 09:24 Kled
 */
public class TestIntrospector {
    //内省机制
    public static void main(String[] args) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        // 通过Introspector.getBeanInfo方法获取指定JavaBean类的BeanInfo信息
        BeanInfo beanInfo = Introspector.getBeanInfo(Student.class);
        // 通过BeanInfo的getPropertyDescriptors方法获取被操作的JavaBean类的所有属性
        // 注意,该属性是通过get/set方法推断出来的属性
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            // 获取属性的名字
            String propName = pd.getName();
            System.out.println(propName);
            // 获取属性的类型
            Class propType = pd.getPropertyType();
            System.out.println(propType);

            // 获取属性对应的get方法
            Method getMethod = pd.getWriteMethod();
            System.out.println(getMethod);

            // 获取属性对应的set方法
            Method setMethod = pd.getReadMethod();
            System.out.println(setMethod);
        }

        Student student = new Student();
        PropertyDescriptor pd = new PropertyDescriptor("name", Student.class);
        Method setMethod = pd.getWriteMethod();
        setMethod.invoke(student, "kled");
        System.out.println(student);
        Method getMethod = pd.getReadMethod();
        System.out.println(getMethod.invoke(student));
    }

    public static class Student{
        private String name;

        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
