package practice;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Scanner;

public class BooksTest {
    public static void main(String[] args) {
        //    addBook();
        //  getAll();
        //    getMaxSales();
        //  modifyStock();
       // getIdInformation();
        delete();
    }

    /**
     * 使用JDBC实现删除订单“15275760194821”的相关信息，注意涉及到两张表
     */
    public static void delete() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //获取连接
            String url = "jdbc:mysql://localhost:3306/test01_bookstore?useUnicode=true&characterEncoding=utf-8";
            connection = DriverManager.getConnection(url, "root", "757601");
            //编写sql语句
            String sql = "delete from orders where id=15275760194821";
            preparedStatement = connection.prepareStatement(sql);
            //执行sql语句
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                System.out.println("执行成功");
            } else {
                System.out.println("执行失败");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                //关闭连接
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }



    /**
     * 从键盘输入用户名，实现查询该用户的订单和订单明细
     */
    public static void getIdInformation() {
        Scanner scanner = new Scanner(System.in);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //获取连接
            String url = "jdbc:mysql://localhost:3306/test01_bookstore?useUnicode=true&characterEncoding=utf-8";
            connection = DriverManager.getConnection(url, "root", "757601");
            //编写sql语句
            String sql = "select * from orders s join order_items o on s.id=o.order_id where s.user_id=(select id from users where username = ?)";

            preparedStatement = connection.prepareStatement(sql);
            String username = scanner.next();
            preparedStatement.setObject(1,username);
            //执行sql语句
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                for (int i = 0; i < 14; i++) {
                    System.out.print(resultSet.getObject(i+1)+"\t");
                }
                System.out.println();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                //关闭连接
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
    /**
     * 修改销量小于10本的图书库存量为100
     */
    public static void modifyStock() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //获取连接
            String url = "jdbc:mysql://localhost:3306/test01_bookstore?useUnicode=true&characterEncoding=utf-8";
            connection = DriverManager.getConnection(url, "root", "757601");
            //编写sql语句
            String sql = "update books set stoct=100 where sales<10";
            preparedStatement = connection.prepareStatement(sql);
            //执行sql语句
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                System.out.println("执行成功");
            } else {
                System.out.println("执行失败");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                //关闭连接
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取销量最大的图书信息
     */
    public static void getMaxSales() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //获取连接
            String url = "jdbc:mysql://localhost:3306/test01_bookstore?useUnicode=true&characterEncoding=utf-8";
            connection = DriverManager.getConnection(url, "root", "757601");
            //编写sql语句
            String sql = "select * from books order by sales desc limit 1";
            preparedStatement = connection.prepareStatement(sql);
            //执行sql语句
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Books books = new Books();
                for (int i = 0; i < 7; i++) {
                    Object object = resultSet.getObject(i + 1);
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    String columnName = metaData.getColumnName(i + 1);
                    Field declaredField = books.getClass().getDeclaredField(columnName);
                    declaredField.setAccessible(true);
                    declaredField.set(books, object);
                }
                System.out.println(books);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                //关闭连接
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    /**
     * 查询所有图书信息
     */
    public static void getAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //获取连接
            String url = "jdbc:mysql://localhost:3306/test01_bookstore?useUnicode=true&characterEncoding=utf-8";
            connection = DriverManager.getConnection(url, "root", "757601");
            //编写sql语句
            String sql = "select * from books";
            preparedStatement = connection.prepareStatement(sql);
            //执行sql语句
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Books books = new Books();
                for (int i = 0; i < 7; i++) {
                    Object object = resultSet.getObject(i + 1);
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    String columnName = metaData.getColumnName(i + 1);
                    Field declaredField = books.getClass().getDeclaredField(columnName);
                    declaredField.setAccessible(true);
                    declaredField.set(books, object);
                }
                System.out.println(books);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                //关闭连接
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    /**
     * 图书馆增操作
     */
    public static void addBook() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //获取连接
            String url = "jdbc:mysql://localhost:3306/test01_bookstore?useUnicode=true&characterEncoding=utf-8";
            connection = DriverManager.getConnection(url, "root", "757601");
            //编写sql语句
            String sql = "insert into books(title,author,price,sales,stoct,img_path) value(?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, "《从入门到放弃》");
            preparedStatement.setObject(2, "柴林燕");
            preparedStatement.setObject(3, 88.8);
            preparedStatement.setObject(4, 0);
            preparedStatement.setObject(5, 100);
            preparedStatement.setObject(6, "upload/books/从入门到放弃.jpg");

            //执行sql语句
            int execute = preparedStatement.executeUpdate();
            if (execute > 0) {
                System.out.println("执行成功");
            } else {
                System.out.println("执行失败");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                //关闭连接
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
