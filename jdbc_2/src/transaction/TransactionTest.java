package transaction;

import org.junit.jupiter.api.Test;
import util.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionTest {
    @Test
    public void testUpdate() {
        String sql1 = "update user_table set balance=balance-100 where user =?";
        update(sql1, "AA");

        System.out.println(10 / 0);
        String sql2 = "update user_table set balance=balance+100 where user =?";
        update(sql2, "BB");

        System.out.println("转账成功");
    }

    //通用的增删改操作
    //sql中占位符的个数与可变形参的长度相同
    public int update(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //1.获取数据库连接
            connection = JDBCUtils.getConnection();
            //2.预编译sql语句，返回PreparedStatement的实例
            preparedStatement = connection.prepareStatement(sql);
            //3.填充占位符
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            //4.执行
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5.资源的关闭
            JDBCUtils.closeResouse(connection, preparedStatement);

        }
        return 0;
    }

    @Test
    public void testUpdateWithTx() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            connection.setAutoCommit(false);
            String sql1 = "update user_table set balance=balance-100 where user =?";
            update2(connection, sql1, "AA");

            System.out.println(10 / 0);
            String sql2 = "update user_table set balance=balance+100 where user =?";
            update2(connection, sql2, "BB");
            System.out.println("转账成功");
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } finally {
            try {
               //6.恢复每次DML操作的自动提交功能
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            JDBCUtils.closeResouse(connection, null);

        }


    }

    //通用的增删改操作--version2.0(考虑事务之后)
    //sql中占位符的个数与可变形参的长度相同
    public int update2(Connection connection, String sql, Object... args) {
        PreparedStatement preparedStatement = null;
        try {
            //1.预编译sql语句，返回PreparedStatement的实例
            preparedStatement = connection.prepareStatement(sql);
            //2.填充占位符
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            //3.执行
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //4.资源的关闭
            JDBCUtils.closeResouse(null, preparedStatement);

        }
        return 0;
    }
    @Test
    public void testTransactionSelect() throws Exception {
        Connection connection = JDBCUtils.getConnection();
        String sql="select user,password,balance from user_table where user=?";
        List<User> cc = getInstance(connection, User.class, sql, "CC");
        System.out.println(cc.toString());
        System.out.println(connection.getTransactionIsolation());


    }
    @Test
    public void testTransactionUpdate() throws Exception {
        Connection connection = JDBCUtils.getConnection();

        String sql="update user_table set balance=? where user=?";
        update2(connection, sql, 5000);
        Thread.sleep(15000);
        System.out.println("修改结束");
    }
//通用的查询操作，version2.0
    public static <T> List<T> getInstance(Connection connection,Class<T> clazz, String sql, Object... args){


        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
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
            JDBCUtils.closeResouse(null, preparedStatement, resultSet);

        }
        return null;

    }
}
