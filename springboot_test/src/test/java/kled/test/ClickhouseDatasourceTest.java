package kled.test;

import com.github.housepower.jdbc.ClickHouseConnection;
import com.github.housepower.jdbc.ClickHouseDriver;
import com.github.housepower.jdbc.ClickhouseJdbcUrlParser;
import com.github.housepower.settings.ClickHouseConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Properties;

/**
 * @author Kled
 * @date 2021/12/27 6:01 PM
 */
public class ClickhouseDatasourceTest extends SpringTestISpringBootApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testClickhouseJdbc() throws SQLException, InterruptedException {
        for (int i = 0; i < 1000; i++) {
            Connection connection = null;
            try {
                connection = dataSource.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
                continue;
            }
            System.out.println(connection.getMetaData().getURL());
            PreparedStatement preparedStatement = connection.prepareStatement("select * from port_flow limit 1");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getString(1));
            }
            connection.close();
            Thread.sleep(2000);
        }
    }

    @Test
    public void testClickhousePing(){
        String jdbcUrl = "jdbc:clickhouse://43.255.229.11:19000/aiop_dev?useSSL=false&autoReconnect=true&useUnicode=true&characterEncoding=UTF8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true";
        boolean pong;
        Properties properties = new Properties();
        properties.put("user", "aiop_readonly");
        properties.put("password", "vooJe4Atoophae");
        ClickHouseDriver driver = new ClickHouseDriver();
        try (ClickHouseConnection connection = driver.connect(jdbcUrl, properties)) {
            while (true){
                pong = connection.ping(Duration.ofSeconds(1));
                Thread.sleep(2000);
                System.out.println(pong);
            }
        } catch (Exception e) {
            logger.error("ClickhouseHikariDataSource -> ping catch error=", e);
            pong = false;
        }
        System.out.println(pong);
    }

    /*
     jdbc:clickhouse://43.255.229.10:19000/aiop_dev?query_timeout=0&connect_timeout=0&charset=UTF-8&client_name=ClickHouse client&tcp_keep_alive=false
     128.1.86.196
     jdbc:clickhouse://43.255.229.10:19000/aiop_dev?query_timeout=0&connect_timeout=0&charset=UTF-8&client_name=ClickHouse client&tcp_keep_alive=false&max_result_rows=0&result_overflow_mode=break
     128.1.86.196
     jdbc:clickhouse://43.255.229.11:19000/aiop_dev?query_timeout=0&connect_timeout=0&charset=UTF-8&client_name=ClickHouse client&tcp_keep_alive=false
     128.1.86.196
     jdbc:clickhouse://43.255.229.10:19000/aiop_dev?query_timeout=0&connect_timeout=0&charset=UTF-8&client_name=ClickHouse client&tcp_keep_alive=false&max_result_rows=0&result_overflow_mode=break
     128.1.86.196
     jdbc:clickhouse://43.255.229.11:19000/aiop_dev?query_timeout=0&connect_timeout=0&charset=UTF-8&client_name=ClickHouse client&tcp_keep_alive=false&max_result_rows=0&result_overflow_mode=break
     128.1.86.196
     */
}
