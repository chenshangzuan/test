package kled.test;

import org.junit.Test;

import java.sql.*;
import java.util.Random;

/**
 * @author: Kled
 * @version: ClickhouseJdbcTest.java, v0.1 2020-12-31 10:55 Kled
 */
public class ClickhouseJdbcTest extends SpringTestISpringBootApplicationTests {

    @Test
    public void testJdbc() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:clickhouse://172.16.5.56:8123/test", "", "");
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from tb_user");
        while (resultSet.next()){
            System.out.println(resultSet.getString("name"));
        }

        String initSql = "insert into tb_user values (?, ?, 18, 'man')";
        PreparedStatement preparedStatement = con.prepareStatement(initSql);
        for (int i = 0; i < 5; i++) {
            Integer randomInt = new Random().nextInt(100) * i;
            preparedStatement.setInt(1, randomInt);
            preparedStatement.setString(2, "man-" + randomInt);
            preparedStatement.execute();
        }
    }
}
