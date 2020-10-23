package java_base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author kled
 * @version $Id: TestRegex.java, v 0.1 2019-03-05 22:16:21 kled Exp $
 */
public class TestRegex {

    private static String REGEX = "(a*b)(foo)";
    private static String INPUT = "aabfooaabfooabfoob";
    private static String REPLACE = "-";

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile(REGEX);

        // get a matcher object
        Matcher matcher = pattern.matcher(INPUT);

        //System.out.println(matcher.matches()); 全局匹配
//        matcher.region(0,6);
//        System.out.println(matcher.regionStart());
//        System.out.println(matcher.regionEnd());
        while(matcher.find()) {
            System.out.println(matcher.group(0)); // matcher.group(0) <==> matcher.group()
            //Prints the offset after the last character matched.
            System.out.println("First Capturing Group, (a*b) Match String end(): "+matcher.end());
            System.out.println("Second Capturing Group, (foo) Match String end(): "+matcher.start(1));
        }
    }
}
