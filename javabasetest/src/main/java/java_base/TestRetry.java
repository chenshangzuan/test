package java_base;

/**
 * @author kled
 * @version $Id: TestRetry.java, v 0.1 2019-01-04 14:06:13 kled Exp $
 */
public class TestRetry {

    public static void main(String[] args) {
        //goto标记，可用任意字符代替
        //retry：需要放在for，while，do...while的前面声明，变量只跟在break和continue后面。
        retry:
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.println(j);
                if(j == 3)
                    //continue retry;
                    break retry;
            }
        }
    }
}
