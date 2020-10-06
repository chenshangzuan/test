package kled.test.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {

    /**
     * mybatis-plus分页插件<br>
     * 文档：http://mp.baomidou.com<br>
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDbType(DbType.MYSQL);
        return paginationInterceptor;
    }

    /**
     * 自定义sql注入器: 扩展BaseMapper通用方法
     * 或者application.properties配置：
     * mybatis-plus.globalConfig.sqlInjector=xxx.GeneralMybatisPlusSqlInjector
     */
//    @Bean
//    public ISqlInjector iSqlInjector() {
//        return new GeneralMybatisPlusSqlInjector();
//    }

    /**
     * 注入主键生成器
     */
    @Bean
    public IKeyGenerator keyGenerator(){
        return new H2KeyGenerator();
    }


}
