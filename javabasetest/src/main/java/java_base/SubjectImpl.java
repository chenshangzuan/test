package java_base;

/**
 * @author kled
 * @version $Id: SubjectImpl.java, v 0.1 2018-12-11 16:46:30 kled Exp $
 */
public class SubjectImpl implements Subject {
    @Override
    public String sayHello() {
        return "hello spi";
    }
}
