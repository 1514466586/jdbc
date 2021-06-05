package dao;

import util.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//封装了针对数据表的通用操作
public abstract class BaseDAO {
    //通用的增删改操作--version2.0(考虑事务之后)
    //sql中占位符的个数与可变形参的长度相同
    public int update(Connection connection, String sql, Object... args) {
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
    //通用的查询操作，version2.0,返回一个对象
    public static <T> T getInstence(Connection connection,Class<T> clazz,String sql, Object... args){
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
            JDBCUtils.closeResouse(null, preparedStatement, resultSet);

        }
        return null;
    }
   //通用的查询操作，version2.0,返回多个对象
    public static <T> List<T> getForList(Connection connection, Class<T> clazz, String sql, Object... args){


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
    //用于查询特殊值的方法
    public <E> E  getValue(Connection connection,String sql,Object ... args){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i+1,args[i]);
            }
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                 return (E)resultSet.getObject(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeResouse(null,preparedStatement,resultSet);

        }
        return null;
    }

}
