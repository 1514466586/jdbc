package preparedStatement;

import bean.Order;
import org.junit.jupiter.api.Test;
import util.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.*;

public class OrderForQuery {
    /*
    针对于表的字段名与类的属性名不相同的情况:
        1．必须声明sql时,使用类的属性名来命名字段的别名
        2．使用ResultSetMetaData时，需要使用getColumnLabel()来替换getColumnName(),
     获取列的别名。
     说明:如果sql中没有给字段其别名，getColumnLabel()获取的就是列名
     */
    @Test
    public void test02(){
        String sql="select order_id orderId,order_name orderName,order_date ordreDate from `order` where order_id=?";
        Order order = orderForQuery(sql, 1);
        System.out.println(order);
    }
    /**
     * 针对order的通用查询操作
     */
    public Order orderForQuery(String sql, Object... args) {
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
                Order order = new Order();
                for (int i = 0; i < columnCount; i++) {
                    //获取每个列的列值:通过resultset
                    Object value = resultSet.getObject(i + 1);
                    //获取每个列的列名通过resultsetmetadata
                    String columnName = metaData.getColumnLabel(i + 1);
                    //反射赋值
                    Field declaredField = Order.class.getDeclaredField(columnName);
                    declaredField.setAccessible(true);
                    declaredField.set(order, value);
                }
                return order;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResouse(connection, preparedStatement, resultSet);

        }
        return null;
    }

    @Test
    public void test01() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "select order_id,order_name,order_date from `order` where order_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, 1);
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            if (resultSet.next()) {
                int id = (int) resultSet.getObject(1);
                String name = (String) resultSet.getObject(2);
                Date date = (Date) resultSet.getObject(3);
                Order order = new Order(id, name, date);
                System.out.println(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResouse(connection, preparedStatement, resultSet);

        }


    }
}
