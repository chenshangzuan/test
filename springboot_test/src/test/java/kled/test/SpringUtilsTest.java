package kled.test;

import com.google.common.base.Joiner;
import kled.test.bean.HelloRequest;
import kled.test.controller.HelloController;
import org.aspectj.lang.annotation.Aspect;
import org.junit.Test;
import org.junit.platform.commons.util.ReflectionUtils;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.objenesis.instantiator.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * @author: Kled
 * @version: SpringUtilsTest.java, v0.1 2020-11-15 20:22 Kled
 */
public class SpringUtilsTest extends SpringTestISpringBootApplicationTests{

    @Autowired
    private HelloController helloController;

    @Test
    public void testAopUtils(){
        System.out.println(AopUtils.isAopProxy(helloController));
        System.out.println(AopUtils.isCglibProxy(helloController));
        System.out.println(AopUtils.isJdkDynamicProxy(helloController));

        AspectJExpressionPointcut pc=new AspectJExpressionPointcut();
        pc.setExpression("execution(public * kled.test.controller.HelloController.testAspect(..))");
        System.out.println(AopUtils.canApply(pc, HelloController.class));
    }

    @Test
    public void testAopProxyUtils(){
        System.out.println(AopProxyUtils.ultimateTargetClass(helloController));
        System.out.println(Joiner.on(",").join(AopProxyUtils.proxiedUserInterfaces(helloController)));
    }

    @Test
    public void testBeanFactoryUtils(){
        System.out.println(Joiner.on(",").join(BeanFactoryUtils.beanNamesForAnnotationIncludingAncestors(applicationContext, Aspect.class)));
        System.out.println(Joiner.on(",").join(BeanFactoryUtils.beanNamesForTypeIncludingAncestors(applicationContext, HelloController.class)));
        System.out.println(BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, HelloController.class));
        System.out.println(BeanFactoryUtils.countBeansIncludingAncestors(applicationContext));

        Stream.of(BeanFactoryUtils.beanNamesForTypeIncludingAncestors(applicationContext, HelloController.class)).forEach(x -> {
            System.out.println(BeanFactoryUtils.isGeneratedBeanName(x));
            //是否为FactoryBean
            System.out.println(BeanFactoryUtils.isFactoryDereference(x));
        });
    }

    @Test
    public void testClassUtils(){
        String className = HelloController.class.getName();
        System.out.println(className);
        System.out.println(ClassUtils.classNameToInternalClassName(className));
        System.out.println(ClassUtils.classNameToResource(className));
        System.out.println(ClassUtils.getExistingClass(this.getClass().getClassLoader(), className));
        System.out.println(ClassUtils.newInstance(HelloController.class));
    }

    @Test
    public void testBeanUtils(){
        HelloRequest helloRequest1 = new HelloRequest();
        helloRequest1.setMsg("111");
        HelloRequest helloRequest2 = new HelloRequest();
        BeanUtils.copyProperties(helloRequest1, helloRequest2);
        System.out.println(helloRequest2);

        //内省机制
        System.out.println(BeanUtils.getPropertyDescriptor(HelloRequest.class, "msg"));
        System.out.println(BeanUtils.findPrimaryConstructor(HelloRequest.class));
        System.out.println(BeanUtils.instantiateClass(HelloRequest.class));
        System.out.println(BeanUtils.findMethod(HelloRequest.class, "setMsg", String.class));

        //基本数据类型判断
        System.out.println("Integer is SimpleProperty = " + BeanUtils.isSimpleProperty(ArrayList.class));
        System.out.println("Integer is isSimpleValueType = " + BeanUtils.isSimpleValueType(Integer.class));
    }

    @Test
    public void testAnnotationUtils(){
        Annotation annotation1 = AnnotationUtils.findAnnotation(HelloController.class, RestController.class);
        System.out.println(AnnotationUtils.getAnnotationAttributes(annotation1));
        System.out.println(AnnotationUtils.getValue(annotation1));
        System.out.println(AnnotationUtils.getDefaultValue(annotation1));

        Method method = ReflectionUtils.findMethod(HelloController.class, "testSession", HttpServletRequest.class).get();
        Annotation annotation2 = AnnotationUtils.findAnnotation(method, RequestMapping.class);
        System.out.println(AnnotationUtils.getAnnotationAttributes(annotation2));

        System.out.println(AnnotationUtils.isCandidateClass(HelloController.class, RequestMapping.class));
        //是否直接注释在类上而不是集成来的
        System.out.println(AnnotationUtils.isAnnotationDeclaredLocally(RequestMapping.class, HelloController.class));

        System.out.println(AnnotationUtils.isInJavaLangAnnotationPackage(annotation1));
        System.out.println(AnnotationUtils.isInJavaLangAnnotationPackage(annotation2));

    }
}
