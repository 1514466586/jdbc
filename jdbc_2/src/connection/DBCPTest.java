package connection;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @className: DBCPTest 测试dbcp数据库连接池技术
 * @description: TODO 类描述
 * @author: niaonao
 * @date: 2021/6/4
 **/
public class DBCPTest {
    //方式一不推荐
    @Test
    public void testDBCP() throws SQLException {

        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName("com.mysql.cj.jdbc.Driver");
        source.setUrl("jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=false&serverTimezone=UTC");
        source.setUsername("root");
        source.setPassword("757601");
        Connection connection = source.getConnection();
        System.out.println(connection);
    }

    private static BasicDataSource source ;

    static {
        Properties properties = new Properties();

        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");

        try {
            properties.load(resourceAsStream);
            source = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //方式二：使用配置文件
    @Test
    public static Connection testDBCP2() throws Exception {
        Connection connection = source.getConnection();
        return connection;

    }


}
