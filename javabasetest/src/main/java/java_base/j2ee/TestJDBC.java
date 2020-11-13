package java_base.j2ee;

import java.sql.*;
import java.util.Enumeration;

/**
 * @author kled
 * @version $Id: TestJDBC.java, v 0.1 2018-12-11 14:14:23 kled Exp $
 */
public class TestJDBC {

    public static void main(String[] args) throws SQLException {
        try {
            //1.加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            //System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");

            //2.建立连接
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/vnetcore", "root", "uEXsn7NZrusBIGKe");
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()){
                System.out.println(drivers.nextElement());
            }

            String sql1 = "select * from vnetcore_cpe where id = 1";
            Statement statement = con.createStatement(); //不支持带参, 一个statement对象只能打开一个ResultSet对象
            ResultSet resultSet1 = statement.executeQuery(sql1);
            //resultSet打开后，resultSet1会自动关闭，无法操作resultSet1的结果
            //ResultSet resultSet = statement.executeQuery(sql1);
            while(resultSet1.next()){
                System.out.println("resultSet1:" + resultSet1.getString(3));
                System.out.println("resultSet1:" + resultSet1.getString("sn"));
                System.out.println("resultSet1:" + resultSet1.getInt("id"));
            }

            String sql2 = "select * from vnetcore_cpe where id = ? and del = ?";
            PreparedStatement preparedStatement1 = con.prepareStatement(sql2);
            preparedStatement1.setInt(1, 1);
            preparedStatement1.setInt(2, 1);

            ResultSet resultSet2 = preparedStatement1.executeQuery();
            while(resultSet2.next()){
                System.out.println("resultSet2:" + resultSet2.getString(2));
            }

            //preparedStatement复用
            preparedStatement1.setInt(1, 2);
            preparedStatement1.setInt(2, 1);
            ResultSet resultSet3 = preparedStatement1.executeQuery();
            while(resultSet3.next()){
                System.out.println("resultSet3:" + resultSet3.getString(2));
            }

            //防SQL注入，自动转义特殊字符
            String sql3 = "select * from vnetcore_cpe where sn = ?";
            PreparedStatement preparedStatement2 = con.prepareStatement(sql3);
            preparedStatement2.setString(1, "11\' OR \'1\'=\'1");
            System.out.println("sql=" + preparedStatement2);
            ResultSet resultSet4 = preparedStatement2.executeQuery();
            while(resultSet4.next()){
                System.out.println("resultSet4:" + resultSet3.getString(2));
            }

            resultSet1.close();//释放结果集资源
            resultSet2.close();//释放结果集资源
            resultSet3.close();//释放结果集资源
            statement.close();//释放传输器资源
            preparedStatement1.close();//释放传输器资源
            preparedStatement2.close();//释放传输器资源
            con.close();//释放连接资源
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
