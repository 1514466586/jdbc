package dao;

import bean.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

//用于规范对于customer表的常用操作
public interface CustomerDAO {
    /**
     * 将customer对象添加到数据库中
     * @param connection
     * @param customer
     */
    void insert(Connection connection, Customer customer);

    /**
     * 针对指定id删除表中的一条记录
     * @param connection
     * @param id
     */
    void deleteById(Connection connection,int id);

    /**
     * 修改数据表中的记录
     * @param connection
     * @param customer
     */
    void updateById(Connection connection,Customer customer);

    /**
     * 针对指定id查询对应的customer对象
     * @param connection
     * @param id
     * @return
     */
    Customer getCustomerById(Connection connection,int id);

    /**
     * 查询表中的所有记录
     * @param connection
     * @return
     */
    List<Customer> getAll(Connection connection);
    /**
     * 返回数据表中的条目数
     */
    Long getCount(Connection connection);

    /**
     * 返回数据表中最大的生日
     * @param connection
     * @return
     */
    Date getMaxBirth(Connection connection);
}
