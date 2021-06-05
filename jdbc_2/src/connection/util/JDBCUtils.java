package connection.util;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 操作数据库的工具类
 */
public class JDBCUtils {
  private static   ComboPooledDataSource cpds = new ComboPooledDataSource("helloc3p0");
    /**
     * @describe 获取数据库连接
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception{

        Connection connection = cpds.getConnection();

        return connection;
    }

    /**
     * @describe 关闭资源
     * @param connection
     * @param preparedStatement
     */
    public static void closeResouse(Connection connection, PreparedStatement preparedStatement){
        //7.关闭资源
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * 关闭资源的操作
     * @param connection
     * @param preparedStatement
     * @param resultSet
     */
    public static void closeResouse(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet){
        //7.关闭资源
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
