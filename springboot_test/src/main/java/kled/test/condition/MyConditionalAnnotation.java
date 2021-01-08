package kled.test.condition;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * @author: Kled
 * @version: MyConditionalAnnotation.java, v0.1 2020-12-29 15:34 Kled
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Conditional(MyCondition.class)
public @interface MyConditionalAnnotation {

    boolean enable() default false;
}
