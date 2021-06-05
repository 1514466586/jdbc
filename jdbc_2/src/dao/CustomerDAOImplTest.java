package dao;

import bean.Customer;
import connection.util.JDBCUtils;
import org.junit.jupiter.api.Test;


import java.sql.Connection;
import java.sql.Date;
import java.util.List;



/**
 * @className: CustomerDAOImplTest
 * @description: TODO 类描述
 * @author: niaonao
 * @date: 2021/6/4
 **/
class CustomerDAOImplTest {
   private CustomerDAOImpl dao= new CustomerDAOImpl();

    @Test
    void insert()  {
        Connection connection=null;
        try {
           connection = JDBCUtils.getConnection();
            Customer customer = new Customer(1,"于小飞","yuxiaofei@126.com",new Date(321424141241L));
            dao.insert(connection,customer);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResouse(connection,null);
        }


    }

    @Test
    void deleteById() {
        Connection connection=null;
        try {
            connection = JDBCUtils.getConnection();
           dao.deleteById(connection,13);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResouse(connection,null);
        }
    }

    @Test
    void updateById() {
        Connection connection=null;
        try {
            connection = JDBCUtils.getConnection();
            Customer customer = new Customer(18,"贝多芬","beiduo@qq.com",new Date(3424414141L));
            dao.updateById(connection,customer);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResouse(connection,null);
        }
    }

    @Test
    void getCustomerById() {
        Connection connection=null;
        try {
            connection = JDBCUtils.getConnection();
            Customer customerById = dao.getCustomerById(connection, 1);
            System.out.println(customerById);


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResouse(connection,null);
        }
    }

    @Test
    void getAll() {
        Connection connection=null;
        try {
            connection = JDBCUtils.getConnection();
            List<Customer> all = dao.getAll(connection);
            all.forEach(System.out::println);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResouse(connection,null);
        }
    }

    @Test
    void getCount() {
        Connection connection=null;
        try {
            connection = JDBCUtils.getConnection();
            Long count = dao.getCount(connection);
            System.out.println(count);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResouse(connection,null);
        }
    }

    @Test
    void getMaxBirth() {
        Connection connection=null;
        try {
            connection = JDBCUtils.getConnection();
            Date maxBirth = dao.getMaxBirth(connection);
            System.out.println(maxBirth);


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResouse(connection,null);
        }
    }
}