package hive;

import java.sql.*;

/**
 * @author Kled
 * @date 2021/6/22 11:43 上午
 */
public class HiveClient {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.hive.jdbc.HiveDriver");
        Connection connection = DriverManager.getConnection("jdbc:hive2://172.16.5.56:10000/default", "root", "");
        PreparedStatement preparedStatement = connection.prepareStatement("select dt from user_click group by dt");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next ()) {
            System.out.println ( resultSet.getString( "dt" ) );
        }
    }
}
