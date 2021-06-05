package practice;

import java.sql.*;
import java.util.Scanner;

public class UserTest {
    public static void main(String[] args) {
    //    addUser();
        logIn();
    }
    /**
     * 添加用户
     */
    public static void addUser() {
        Scanner scanner = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            scanner = new Scanner(System.in);
            System.out.println("请输入用户名：");
            String user = scanner.next();
            System.out.println("请输入密码：");
            String password = scanner.next();
            System.out.println("请输入邮箱：");
            String email = scanner.next();
            //注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //获取连接
            String url="jdbc:mysql://localhost:3306/test01_bookstore?useUnicode=true&characterEncoding=utf-8";
            connection = DriverManager.getConnection(url, "root", "757601");
            //编写sql
            String sql="insert into users(username,password,email) value(?,?,?)";
            //创建preparedStatement对象
            preparedStatement = connection.prepareStatement(sql);
            //填充占位符
            preparedStatement.setObject(1,user);
            preparedStatement.setObject(2,password);
            preparedStatement.setObject(3,email);
            //执行sql
            int i = preparedStatement.executeUpdate();
            if (i>0){
                System.out.println("执行成功");
            }else {
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
            if (scanner != null) {
                scanner.close();
            }
        }

    }
    /**
     * 模拟登录
     */
    public static void logIn() {
        Scanner scanner = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            scanner = new Scanner(System.in);
            System.out.println("请输入用户名：");
            String user = scanner.next();
            System.out.println("请输入密码：");
            String password = scanner.next();
            //注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //获取连接
            String url="jdbc:mysql://localhost:3306/test01_bookstore?useUnicode=true&characterEncoding=utf-8";
            connection = DriverManager.getConnection(url, "root", "757601");
            //编写sql
            String sql="select username,password from users where username=? and password=?";
            //创建preparedStatement对象
            preparedStatement = connection.prepareStatement(sql);
            //填充占位符
            preparedStatement.setObject(1,user);
            preparedStatement.setObject(2,password);
            //执行sql
            ResultSet i = preparedStatement.executeQuery();
            if (i.next()){
                System.out.println("登录成功");
            }else {
                System.out.println("登录失败");
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
            if (scanner != null) {
                scanner.close();
            }
        }

    }
}
