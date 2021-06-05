package dao;

import bean.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLClientInfoException;
import java.util.List;

/**
 * @className: CustomerDAOImpl
 * @description: TODO 类描述
 * @author: niaonao
 * @date: 2021/6/4
 **/
public class CustomerDAOImpl extends BaseDAO implements CustomerDAO{
    @Override
    public void insert(Connection connection, Customer customer) {
        String sql="insert into customers(name,email,birth) values(?,?,?);";

        update(connection,sql,customer.getName(),customer.getEmail(),customer.getBirth());
    }

    @Override
    public void deleteById(Connection connection, int id) {
        String sql="delete from customers where id=?;";
            update(connection,sql,id);
    }

    @Override
    public void updateById(Connection connection, Customer customer) {
        String sql ="update customers set name=?,email=?,birth=? where id=?;";
        update(connection,sql,customer.getName(),customer.getEmail(),customer.getBirth(),customer.getId());
    }

    @Override
    public Customer getCustomerById(Connection connection, int id) {
        String sql="select id,name,email,birth from customers where id =?";
        Customer customer = getInstence(connection, Customer.class, sql, id);

        return customer;
    }

    @Override
    public List<Customer> getAll(Connection connection) {
        String sql="select id,name,email,birth from customers ";
        List<Customer> list = getForList(connection, Customer.class, sql);
        return list;

    }

    @Override
    public Long getCount(Connection connection) {
        String sql="select count(*) from customers";
        return getValue(connection, sql);
    }

    @Override
    public Date getMaxBirth(Connection connection) {
        String sql="select max(birth) from customers";
      return   getValue(connection,sql);
    }
}
