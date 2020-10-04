package localTest;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

/**
 * @author kled
 * @version $Id: TestJDBC.java, v 0.1 2018-12-11 14:14:23 kled Exp $
 */
public class TestJDBC {

    public static void main(String[] args) throws SQLException {
        Connection con = null;
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            // Driver.class
            // static {
            //    try {
            //        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            //    } catch (SQLException var1) {
            //        throw new RuntimeException("Can't register driver!");
            //    }
            // }
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/test", "root", "root");
            // DriverManager.class
            // static {
            //    loadInitialDrivers(); //SPI默认去查询引用jar包下META-INF/services/java.sql.Driver文件, 内部为具体的实现类
            //    println("JDBC DriverManager initialized");
            // }
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()){
                System.out.println(drivers.nextElement());
                //com.mysql.fabric.jdbc.FabricMySQLDriver@79fc0f2f
                //com.mysql.jdbc.Driver@50040f0c
            }
            String sql = "select * from provider_handler_strategy where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, 1);
            Statement statement = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }
}
