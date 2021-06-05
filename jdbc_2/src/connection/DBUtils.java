package connection;

import bean.Customer;
import connection.util.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

/**
 * @className: DBUtils 封装了数据库的增删改查操作
 * @description: TODO 类描述
 * @author: user
 * @date: 2021/6/5
 **/
public class DBUtils {
    @Test
    public void testInsert() throws Exception {
        QueryRunner queryRunner = new QueryRunner();
        Connection connection = DruidTest.getConnection();
        String sql = "insert into customers(name,email,birth) values(?,?,?)";
        queryRunner.update(connection, sql, "蔡徐坤", "caixukun@123.com", "1998-3-4");

    }

    @Test
    public void testQuery1() throws Exception {
        QueryRunner queryRunner = new QueryRunner();
        Connection connection = DruidTest.getConnection();
        String sql = "select id,name,email,birth from customers where id=?";
        BeanHandler<Customer> beanHandler = new BeanHandler(Customer.class);
        Customer query = queryRunner.query(connection, sql, beanHandler, 23);
        System.out.println(query);
        JDBCUtils.closeResouse(connection, null, null);
    }

    @Test
    public void testQuery2() throws Exception {
        QueryRunner queryRunner = new QueryRunner();
        Connection connection = DruidTest.getConnection();
        String sql = "select id,name,email,birth from customers where id<?";
        BeanListHandler<Customer> customerBeanListHandler = new BeanListHandler(Customer.class);
        List<Customer> query = queryRunner.query(connection, sql, customerBeanListHandler, 20);
        query.forEach(System.out::println);
    }
    /*
    maphander:将字段与对应的值作为map中的key和value
     */
    @Test
    public void testQuery3() throws Exception {
        QueryRunner queryRunner = new QueryRunner();
        Connection connection = DruidTest.getConnection();
        String sql = "select id,name,email,birth from customers where id<?";
        MapHandler mapHandler = new MapHandler();
        Map<String, Object> query = queryRunner.query(connection, sql, mapHandler, 20);
        System.out.println(query);
    }
    @Test
    public void testQuery4() throws Exception {
        QueryRunner queryRunner = new QueryRunner();
        Connection connection = DruidTest.getConnection();
        String sql = "select id,name,email,birth from customers where id<?";
        MapListHandler mapHandler = new MapListHandler();
        List<Map<String, Object>> query = queryRunner.query(connection, sql, mapHandler, 20);
        query.forEach(System.out::println);
    }
    @Test
    public void testQuery5() throws Exception {
        QueryRunner queryRunner = new QueryRunner();
        Connection connection = DruidTest.getConnection();
        String sql = "select count(*) from customers ";
        ScalarHandler<Long> objectScalarHandler = new ScalarHandler<>();
        Long query = queryRunner.query(connection, sql, objectScalarHandler);
        System.out.println(query);
        JDBCUtils.closeResouse(connection,null);
    }

    /**
     *用于查询特殊值
     * @throws Exception
     */
    @Test
    public void testQuery6() throws Exception {
        QueryRunner queryRunner = new QueryRunner();
        Connection connection = DruidTest.getConnection();
        String sql = "select max(birth) from customers ";
        ScalarHandler objectScalarHandler = new ScalarHandler();
        Date date = null;
        date =(Date) queryRunner.query(connection, sql, objectScalarHandler);
        System.out.println(date);
        JDBCUtils.closeResouse(connection,null);
    }
    @Test
    public void testQuery7() throws Exception {
        QueryRunner queryRunner = new QueryRunner();
        Connection connection = DruidTest.getConnection();
        String sql = "select id,name,email,birth from customers where id=?";
        ResultSetHandler<Customer> resultSetHandler;
        resultSetHandler = new ResultSetHandler<>() {
            @Override
            public Customer handle(ResultSet resultSet) throws SQLException {
                return new Customer(12,"sda","2234@rwe.com",new Date(42411231231L));
            }
        };
        Customer query = queryRunner.query(connection, sql, resultSetHandler,11);
        System.out.println(query);
        util.JDBCUtils.closeResouse(connection,null);
    }

}
