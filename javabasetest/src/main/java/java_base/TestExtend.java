package java_base;

/**
 * @author chenpc
 * @version $Id: TestExtend.java, v 0.1 2018-04-15 11:44:26 chenpc Exp $
 */
public class TestExtend {

    private static TestExtend t = new TestExtend(); //父类静态属性加载>父类静态代码块>子类静态属性加载>子类静态代码块

    static {
        System.out.println("static module");
    }

    public TestExtend() {
        System.out.println("start");
    }

    public void test(){
        System.out.println("father class");
    }

    public static void main(String[] args) {
        TestExtend test = new SubClass();
        test.test();
    }
}


class SubClass extends TestExtend{

    private static SubClass t = new SubClass();

    static {
        System.out.println("sub static module");
    }

    public SubClass() {
        System.out.println("sub start");
    }

    public void test(){
        System.out.println("sub class");
    }
}
