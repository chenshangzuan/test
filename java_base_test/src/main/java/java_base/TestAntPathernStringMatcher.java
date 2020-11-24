package java_base;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author chenpc
 * @version $Id: java_base.TestAntPathernStringMatcher.java, v 0.1 2018-04-25 10:52:37 chenpc Exp $
 */
public class TestAntPathernStringMatcher {

    private static final Pattern GLOB_PATTERN = Pattern.compile("\\?|\\*|\\{((?:\\{[^/]+?\\}|[^/{}]|\\\\[{}])+?)\\}");

    public static void main(String[] args) {
        PathMatcher pathMatcher = new AntPathMatcher();
        Map<String, String> result1 = pathMatcher.extractUriTemplateVariables("/hotels/{hotel}", "/hotels/1");
        System.out.println(result1);

        Map<String, String> result2 = pathMatcher.extractUriTemplateVariables("/docs/{d}/{a}/**", "/docs/cvs/commit");
        //GLOB_PATTERN 提取{variableName} ->pattern<(.*)> -> Map<variableName, value>
        System.out.println(result2);

        System.out.println(pathMatcher.match("/hotels/{hotel}", "/hotels/1"));

        System.out.println(pathMatcher.match("/**/1", "/hotels/1"));

        //System.out.println(pathMatcher.extractPathWithinPattern("/d?cs/*", "/docs/cvs/commit")); //包含？和*

    }
}
