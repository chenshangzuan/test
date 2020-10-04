package localTest;

import java.util.Arrays;
import java.util.List;

/**
 * @author kled
 * @version $Id: TestTryCatch.java, v 0.1 2018-09-18 20:51:14 kled Exp $
 */
public class TestTryCatch {

    public static void main(String[] args) {
        List<String> lists = Arrays.asList("test1","test2","test3","test4","test5");

        for (String s : lists) {
            try{
                if(s.equals("test3"))
                    throw new NullPointerException();
            }finally {
                //break or exception, do this
                System.out.println(s);
            }
        }
    }
}
