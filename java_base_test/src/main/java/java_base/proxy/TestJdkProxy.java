package java_base.proxy;/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2018 All Rights Reserved.
 */


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author chenpc
 * @version $Id: java_base.proxy.TestJdkProxy.java, v 0.1 2018-03-13 10:52:45 chenpc Exp $
 */
public class TestJdkProxy {

    public static void main(String[] args) {
        RealSubject realSubject = new RealSubject();
        Handler handler = new Handler(realSubject);
        System.out.println("realSubject class:"+realSubject.getClass());
        // https://blog.csdn.net/zknxx/article/details/77919332
        // Proxy.newProxyInstance -> proxyClassCache.get(loader, interfaces) -> ProxyGenerator.generateProxyClass(proxyName, interfaces, accessFlags);
        // 动态生成代理类 Proxy{\d+} extends Proxy implements UserTargetInterface
        Subject proxy = (Subject) Proxy.newProxyInstance(realSubject.getClass().getClassLoader(),realSubject.getClass().getInterfaces(),handler);
        System.out.println("proxy class:"+proxy.getClass());
        System.out.println(proxy.sayHello());
        //System.out.println(proxy.toString());
        //生成代理类的class文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
    }

    static class RealSubject implements Subject{
        private String s = "hello proxy";

        public String sayHello(){
            return new String(s).intern();
        }

        //私有方法不会被代理
        private void test(){
            System.out.println();
        }

        void test2(){
            System.out.println("test2");
        }

        public static void test3(){
            System.out.println("test3");
        }
    }

    static class Handler implements InvocationHandler{

        private Subject subject;
        public Handler(Subject subject){
            this.subject = subject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if("sayHello".equals(method.getName())){
                System.out.println("i'm a proxy. do method sayHello");
                //return "aaa";
                return method.invoke(subject,args); //执行被代理类的原方法，需传入被代理对象实例，如果是静态方法则传null!!!!
            }
            return proxy; //若返回proxy， 则可连续调用该代理（此次需保证方法的返回类型为该接口）
        }
    }

}
