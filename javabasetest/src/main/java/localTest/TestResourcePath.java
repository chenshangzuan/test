package localTest;

/**
 * @author chenpc
 * @version $Id: localTest.TestResourcePath.java, v 0.1 2018-05-21 11:37:14 chenpc Exp $
 */
public class TestResourcePath {

    public static void main(String[] args) {

        TestResourcePath testResourcePath = new TestResourcePath();
        System.out.println(System.getProperty("user.dir")); //项目工程实际目录

        System.out.println(testResourcePath.getClass().getResource("spring/test/Application.class").getPath()); //当前类文件 相对位置

        System.out.println(testResourcePath.getClass().getResource("/").getPath()); //绝对位置

        //Class.getResourceAsStream最终调用是ClassLoader.getResourceAsStream,只是对参数进行了调整。如果参数已/开头，则去除/，否则把当前类的包名加在参数的前面。
        //即可视为直接从target/classes目录开始(maven工程为例，classPath定义为target/classes)

        System.out.println(testResourcePath.getClass().getClassLoader().getResource("localTest.Task.class").getPath());
    }
}
