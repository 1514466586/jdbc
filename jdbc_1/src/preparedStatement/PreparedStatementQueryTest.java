package preparedStatement;

import bean.Customer;
import bean.Order;
import org.junit.jupiter.api.Test;
import statementcrud.User;
import util.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 使用PreparedStatement实现针对与不同表的通用的查询操作
 * @author 15144
 */
public class PreparedStatementQueryTest {
    // 使用Statement的弊端：需要拼写sql语句，并且存在SQL注入的问题
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入用户名：");
        String user = scanner.next();
        System.out.print("请输入密码：");
        String password = scanner.next();
        String sql="SELECT user,password FROM user_table WHERE user=? and password=?;";
        User returnUser = getInstence(User.class,sql,user,password);
        if(returnUser!=null){
            System.out.println("登录成功");
        }else {
            System.out.println("用户名不存在或者密码错误");
        }
    }
    @Test
    public void test02(){
        String sql="select id,name,email from customers where id <?";
        List<Customer> list = getForList(Customer.class, sql, 12);
        list.forEach(System.out::println);
    }
    public static <T> List<T> getForList(Class<T> clazz, String sql, Object... args){

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);

            }
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            ArrayList<T> list = new ArrayList<>();
            while (resultSet.next()) {
                T t = clazz.newInstance();

                for (int i = 0; i < columnCount; i++) {
                    //获取每个列的列值:通过resultset
                    Object value = resultSet.getObject(i + 1);
                    //获取每个列的列名通过resultsetmetadata
                    String columnName = metaData.getColumnLabel(i + 1);
                    //反射赋值
                    Field declaredField = clazz.getDeclaredField(columnName);
                    declaredField.setAccessible(true);
                    declaredField.set(t, value);
                }
                list.add(t);


            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResouse(connection, preparedStatement, resultSet);

        }
        return null;

    }
    @Test
    public void test01(){
        String sql="select id,name,email from customers where id =?";
        Customer customer = getInstence(Customer.class, sql, 12);
        System.out.println(customer);
    }
        public static <T> T getInstence(Class<T> clazz,String sql, Object... args){
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            try {
                connection = JDBCUtils.getConnection();
                preparedStatement = connection.prepareStatement(sql);
                for (int i = 0; i < args.length; i++) {
                    preparedStatement.setObject(i + 1, args[i]);

                }
                resultSet = preparedStatement.executeQuery();
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                if (resultSet.next()) {
                    T t = clazz.newInstance();

                    for (int i = 0; i < columnCount; i++) {
                        //获取每个列的列值:通过resultset
                        Object columnVal = resultSet.getObject(i + 1);
                        //获取每个列的列名通过resultsetmetadata
                        String columnName = metaData.getColumnLabel(i + 1);
                        //反射赋值
                        Field field = clazz.getDeclaredField(columnName);
                        field.setAccessible(true);
                        field.set(t, columnVal);
                    }
                    return t;

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                JDBCUtils.closeResouse(connection, preparedStatement, resultSet);

            }
            return null;
        }
}
