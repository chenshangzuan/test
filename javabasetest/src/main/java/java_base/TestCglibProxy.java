/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package java_base;

import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class TestCglibProxy {

    public static void main(String[] args) {
        //打印CGLIB代理类
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/kled/git/test/com/sun/proxy/");

        //创建一个Enhancer对象
        Enhancer enchaner = new Enhancer();
        //设置被代理的类
        enchaner.setSuperclass(TestJdkProxy.RealSubject.class);
        //创建一个回调接口
        Callback interceptor = new MethodInterceptor() {

            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
                    throws Throwable {
                System.out.println("proxy class:" + obj.getClass());
                System.out.println("原方法名是 ：" + method.getName());
                System.out.println("原方法声明的类为 " + method.getDeclaringClass());
                System.out.println("我是 " + (String) proxy.invokeSuper(obj, args));
                System.out.println("我调用结束了");
                return null;
            }
        };
        enchaner.setCallback(interceptor);
        TestJdkProxy.RealSubject student = (TestJdkProxy.RealSubject) enchaner.create();
        student.sayHello();
        student.test2();
        ///Users/kled/git/test/com/sun/proxy/
    }

}
