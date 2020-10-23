package java_base;

/**
 * @author kled
 * @version $Id: TestHashCode.java, v 0.1 2019-01-03 15:37:25 kled Exp $
 */
public class TestHashCode {

    private Integer i = 1;

    public static void main(String[] args) {
        TestHashCode testHashCode1 = new TestHashCode();
        TestHashCode testHashCode2 = new TestHashCode();
        System.out.println(testHashCode1.hashCode());
        System.out.println(testHashCode2.hashCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TestHashCode that = (TestHashCode) o;

        return i != null ? i.equals(that.i) : that.i == null;
    }

    @Override
    public int hashCode() {
        return i != null ? i.hashCode() : 0;
    }
}
