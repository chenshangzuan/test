package java_base.j2ee;

import java_base.proxy.Subject;

import java.util.ServiceLoader;

/**
 * @author kled
 * @version $Id: TestSPI.java, v 0.1 2018-12-11 16:40:34 kled Exp $
 */
public class TestSPI {
    public static void main(String[] args) {
        ServiceLoader<Subject> loaders = ServiceLoader.load(Subject.class);
        for (Subject loader :loaders) {
            System.out.println(loader.sayHello());
        }
    }
}

