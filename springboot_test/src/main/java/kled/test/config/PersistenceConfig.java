package kled.test.config;

import com.github.housepower.jdbc.ClickhouseClusterDataSource;
import com.github.housepower.jdbc.ClickhouseClusterProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class PersistenceConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClass;

    @Bean
    public DataSource clickHouseDataSource() {
        ClickhouseClusterProperties.Builder builder = ClickhouseClusterProperties.Builder.clickhouseClusterProperties();
        ClickhouseClusterProperties properties = builder.withUsername(username)
                .withPassword(password)
                .withDriverClassName(driverClass)
                .withMaximumPoolSize(50)
                .withMinimumIdle(10)
                .withPoolConnectionTimeout(5000L)
                .build();
        return new ClickhouseClusterDataSource(url, properties);
    }
}
