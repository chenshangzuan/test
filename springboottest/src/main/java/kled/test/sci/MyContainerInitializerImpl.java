package kled.test.sci;

import javax.servlet.ServletContext;

/**
 * @author: Kled
 * @version: MyContainerInitializerImpl.java, v0.1 2020-11-07 17:15 Kled
 */
public class MyContainerInitializerImpl implements MyContainerInitializer {
    @Override
    public void onStartup(ServletContext context) {
        context.setAttribute("MyListContainerInitializer",this);
        System.out.println("MyListContainerInitializer Init ...");
    }
}
