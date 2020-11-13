package kled.test.sci;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.lang.reflect.Constructor;
import java.util.Set;

/**
 * @author: Kled
 * @version: MySCI.java, v0.1 2020-11-07 17:13 Kled
 */
@HandlesTypes(MyContainerInitializer.class)
public class MySCI implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        for (Class<?> clazz : set) {
            if(!clazz.isInterface()){
                try {
                    System.out.println(clazz);
                    Constructor<?> constructor = clazz.getConstructor();
                    Object instance = constructor.newInstance();
                    MyContainerInitializer containerInitalizer = (MyContainerInitializer)instance;
                    containerInitalizer.onStartup(servletContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(">>>>>>");
    }
}
