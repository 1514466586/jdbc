package connection;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @className: C3P0Test
 * @description: TODO 类描述
 * @author: niaonao
 * @date: 2021/6/4
 **/
public class C3P0Test {
   //方式一
   @Test
    public void testGetConnection() throws Exception {
       ComboPooledDataSource cpds = new ComboPooledDataSource();
       //获取c3p0数据库连接池
       cpds.setDriverClass("com.mysql.jdbc.Driver"); //loads the jdbc driver
       cpds.setJdbcUrl("jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=false&serverTimezone=UTC");
       cpds.setUser("root");
       cpds.setPassword("757601");
       cpds.setInitialPoolSize(10);//初始时数据库连接数
       Connection connection = cpds.getConnection();
       System.out.println(connection);
      DataSources.destroy(cpds);
   }
   //方式二,使用配置文件
   @Test
   public void testGetConnection2() throws Exception {
      ComboPooledDataSource cpds = new ComboPooledDataSource("helloc3p0");
      Connection connection = cpds.getConnection();
      System.out.println(connection);
   }
}
