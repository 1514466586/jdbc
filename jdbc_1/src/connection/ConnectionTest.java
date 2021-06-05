package connection;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {
    //方式一
    @Test
    public  void  test01() throws SQLException {
        //获取driver的实现类对象
        Driver driver=new com.mysql.jdbc.Driver();
        String url="jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8";
        Properties info=new Properties();
        info.setProperty("user","root");
        info.setProperty("password","757601");
        Connection connect = driver.connect(url, info);
        System.out.println(connect);
    }
    //方式二,不再出现第三方的api，使程序具有更好的可移植性
    @Test
    public void test02() throws Exception {
        //1.获取driver的实现类对象,使用反射
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver =(Driver) clazz.newInstance();
        //2.提供要连接的数据库
        String url="jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8";
        //3.提供连接需要的用户名和密码
        Properties info=new Properties();
        info.setProperty("user","root");
        info.setProperty("password","757601");
        Connection connect = driver.connect(url, info);
        System.out.println(connect);

    }
    //方式三 DriverManager替换Driver
    @Test
    public void test03() throws Exception {
        //1.获取driver的实现类对象
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver =(Driver) clazz.newInstance();
        //2.提供三个连接的基本信息
        String url="jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8";
        String user="root";
        String password="757601";
        //注册驱动
        DriverManager.registerDriver(driver);
        //获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }
    //方式四 ，优化方式三,省略创建运行时对象和注册驱动，只加载，不显示的注册驱动
    @Test
    public void test04() throws Exception {
        //1.获取driver的实现类对象
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
       // Driver driver =(Driver) clazz.newInstance();
        //2.提供三个连接的基本信息
        String url="jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8";
        String user="root";
        String password="757601";
        //注册驱动
      //  DriverManager.registerDriver(driver);
        //获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }
    //方式五
    // 1.实现数据与代码的分离，实现了解耦
    // 2.如果需要修改配置信息，可以避免程旭崇信打包
    @Test
    public void  test05() throws Exception {
        //1.读取配置文件的4个基本信息
        InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties pros = new Properties();
        pros.load(is);
        String user = pros.getProperty("user");
        String passWord = pros.getProperty("passWord");
        String url = pros.getProperty("url");
        String driverClass = pros.getProperty("driverClass");
        //2.加载驱动
        Class.forName(driverClass);
        //3.获取连接
        Connection connection = DriverManager.getConnection(url, user, passWord);
        System.out.println(connection);
    }
}
