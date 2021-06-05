package preparedStatement;

import connection.ConnectionTest;
import org.junit.jupiter.api.Test;
import util.JDBCUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

//增删改查
public class PreparedStatementUpdateTest {
    @Test
    public void testCommonUpdate(){
//        String sql="delete from customers where id=?";
//        update(sql,3);
        String sql="update `order` set order_name =? where order_id=?";
        update(sql,"DD","2");
    }
    //通用的增删改操作
    //sql中占位符的个数与可变形参的长度相同
    public void update(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //1.获取数据库连接
            connection = JDBCUtils.getConnection();
            //2.预编译sql语句，返回PreparedStatement的实例
            preparedStatement = connection.prepareStatement(sql);
            //3.填充占位符
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            //4.执行
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally
        {
            //5.资源的关闭
            JDBCUtils.closeResouse(connection, preparedStatement);

        }

    }

    //修改记录
    @Test
    public void test02() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //1.获取数据库连接
            connection = JDBCUtils.getConnection();
            //2.预编译sql语句，返回PreparedStatement的实例
            String sql = "update customers set name= ? where id=?";
            preparedStatement = connection.prepareStatement(sql);
            //3.填充占位符
            preparedStatement.setObject(1, "莫扎特");
            preparedStatement.setObject(2, 18);
            //4.执行
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5.资源的关闭
            JDBCUtils.closeResouse(connection, preparedStatement);
        }

    }

    //向数据表添加一条数据
    @Test
    public void test01() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //1.读取配置文件的4个基本信息
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
            Properties pros = new Properties();
            pros.load(is);
            String user = pros.getProperty("user");
            String passWord = pros.getProperty("passWord");
            String url = pros.getProperty("url");
            String driverClass = pros.getProperty("driverClass");
            //2.加载驱动
            Class.forName(driverClass);
            //3.获取连接
            connection = DriverManager.getConnection(url, user, passWord);
            // System.out.println(connection);
            //4.获取实例预编译sql语句
            String sql = "insert into customers(name,email,birth) values(?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            //5.填充占位符
            preparedStatement.setString(1, "哪吒");
            preparedStatement.setString(2, "nezha@qq.com");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parse = simpleDateFormat.parse("1000-1-1");
            long time = parse.getTime();
            preparedStatement.setDate(3, new Date(time));
            //6.执行sql
            preparedStatement.execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
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
    }
}
