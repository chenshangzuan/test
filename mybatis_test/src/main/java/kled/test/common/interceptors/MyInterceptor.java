package kled.test.common.interceptors;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author: Kled
 * @version: MyInterceptor.java, v0.1 2020-11-21 22:20 Kled
 */
@Intercepts({@Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}
)})
public class MyInterceptor implements Interceptor {
    private Properties properties;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement)args[0];
        Object parameter = args[1];
        Executor executor = (Executor)invocation.getTarget();
        System.out.println("MyInterceptor, sql=" + ms.getSqlSource() + ", param=" + parameter + " ,properties=" + properties);
        return executor.update(ms, parameter);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        //对应mybatis-config.xml配置中，<plugin> -> <Properties>配置
        this.properties = properties;
    }
}
