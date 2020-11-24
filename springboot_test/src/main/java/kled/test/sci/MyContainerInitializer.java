package kled.test.sci;

import javax.servlet.ServletContext;

/**
 * @author: Kled
 * @version: MyContainerInitializer.java, v0.1 2020-11-07 17:13 Kled
 */
public interface MyContainerInitializer {
    void onStartup(ServletContext context);
}
