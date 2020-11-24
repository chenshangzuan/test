package java_base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author chenpc
 * @version $Id: java_base.TestException.java, v 0.1 2018-04-24 10:58:11 chenpc Exp $
 */
public class TestException {

    private static final Logger logger = LoggerFactory.getLogger(TestException.class);

    public static void test1() throws RuntimeException{ //RuntimeException可以避免，不需要try...catch或throw
        throw new NullPointerException();
    }

    public static void test2() throws IOException{ //非RuntimeException或者Error 无法可以避免，需要try...catch或throw
    }

    public static void main(String[] args) {
        test1();

        try{
            test2();
        }catch(Exception ex){
            logger.info("Exception occurred in java_base.TestException->main, ex=", ex);
        }
    }
}
