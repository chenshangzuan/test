package kled.test.condition;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

/**
 * @author: Kled
 * @version: MyCondition.java, v0.1 2020-12-29 15:29 Kled
 */
public class MyCondition extends SpringBootCondition {
    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        //系统和注解内的任意一个为false，不注入
        boolean sysEnable = Boolean.valueOf(context.getEnvironment().getProperty("mycondition.enable", "true"));
        if (!sysEnable) {
            return new ConditionOutcome(false, "System config's enable is false");
        }

        Map<String, Object> valueMap = metadata.getAnnotationAttributes(MyConditionalAnnotation.class.getName());
        if (valueMap != null && valueMap.containsKey("enable")) {
            if (!Boolean.valueOf(valueMap.get("enable").toString())) {
                return new ConditionOutcome(false, "MyConditionalAnnotation's enable is false");
            }
        }
        return new ConditionOutcome(true, "");
    }
}
