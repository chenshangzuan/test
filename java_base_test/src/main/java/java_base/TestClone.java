package java_base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * @author kled
 * @version $Id: TestClone.java, v 0.1 2018-10-25 22:11:26 kled Exp $
 */
public class TestClone implements Cloneable {
    public Test11 test1 = new Test11();

    @Override
    protected TestClone clone() throws CloneNotSupportedException {
        TestClone testClone = null;
        testClone = (TestClone) super.clone();
        testClone.test1 = (Test11) test1.clone();
        return testClone;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        TestClone testClone = new TestClone();
        TestClone testClone1 = testClone.clone();
        testClone.test1.a = 2;
        System.out.println(testClone1.test1.a); //深度拷贝
    }

    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        List<T> dest = (List<T>) in.readObject();
        return dest;
    }
}

class Test11 implements Cloneable{
    public int a =1;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}


