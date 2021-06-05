package preparedStatement;

import bean.Customer;
import org.junit.jupiter.api.Test;
import util.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * 针对customer表的查询操作
 */
public class CustomerQuery {
    @Test
    public void test03(){
        String sql="select id,name,birth,email from customers where id=?";
        Customer customer = queryForQuery(sql, 13);
        System.out.println(customer);

        String sql1="select id,name,birth from customers where name=?";
        Customer customer1 = queryForQuery(sql1, "周杰伦");
        System.out.println(customer1);
    }
    /**
     * 针对customers的通用查询操作
     */
    public Customer queryForQuery(String sql,Object ... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i+1,args[i]);
            }
            resultSet = preparedStatement.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            //获取结果集中的列数
            int columnCount = resultSetMetaData.getColumnCount();
            if (resultSet.next()){
                Customer customer = new Customer();
                //处理结果集每一行数据中的每一个列
                for (int i = 0; i < columnCount; i++) {
                    //获取列值
                    Object columnValue = resultSet.getObject(i + 1);
                    //获取每个列的列名
                    String columnName = resultSetMetaData.getColumnName(i + 1);


                    //给customer对象指定的某个属性赋值为 value:通过反射
                    Field field = Customer.class.getDeclaredField(columnName);
                    field.setAccessible(true);

                    field.set(customer,columnValue);
                }
                return customer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResouse(connection,preparedStatement,resultSet);

        }

        return null;

    }
    @Test
    public void testQuery1() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql="select id,name,email,birth from customers where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1,1);
            //执行sql,并返回结果集
            resultSet = preparedStatement.executeQuery();
            //处理结果集
            if (resultSet.next()) {//判断结果集下一条是否有数据，有数据返回true指针下移
                //获取当前这条数据的字段值
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                Date birth = resultSet.getDate(4);
                //方式一 直接显示
             //   System.out.println("id="+id+",name="+name+",email"+email+",birth="+birth);
                //方式二：
          //      Object[] data=new Object[] {id,name,email,bitth};
                //方式三 将数据封装成一个对象
                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResouse(connection,preparedStatement,resultSet);

        }




    }

}
