package java_base;

import java.lang.reflect.InvocationTargetException;

import org.codehaus.commons.compiler.CompileException;
import org.codehaus.commons.compiler.IScriptEvaluator;
import org.codehaus.janino.ExpressionEvaluator;
import org.codehaus.janino.ScriptEvaluator;

/**
 * @author Kled
 * @date 2021/9/1 4:40 下午
 */
public class TestJanino {

    public static void main(String[] args) throws InvocationTargetException, CompileException {
        scriptEvaluator();
        expressionEvaluator();
    }

    public static void expressionEvaluator() throws CompileException, InvocationTargetException {
        // 首先定义一个表达式模拟器ExpressionEvaluator对象
        ExpressionEvaluator ee = new ExpressionEvaluator();

        // 定义一个算术表达式，表达式中需要有2个int类型的参数a和b
        String expression = "2 * (a + b)";
        ee.setParameters(new String[]{"a", "b"}, new Class[]{int.class, int.class});

        // 设置表达式的返回结果也为int类型
        ee.setExpressionType(int.class);

        // 这里处理（扫描，解析，编译和加载）上面定义的算数表达式.
        ee.cook(expression);

        // 根据输入的a和b参数执行实际的表达式计算过程
        int result = (Integer) ee.evaluate(new Object[]{19, 23});
        System.out.println(expression + " = " + result);
    }

    public static void scriptEvaluator() throws CompileException, InvocationTargetException {
        String content="System.out.println(\"Hello world\");";
        IScriptEvaluator evaluator = new ScriptEvaluator();
        evaluator.cook(content);
        evaluator.evaluate(null);
    }

}
