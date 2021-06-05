package practice;

import util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Scanner;

public class Exer1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String name = scanner.next();
        System.out.println("请输入邮箱：");
        String email = scanner.next();
        System.out.println("请输入生日：");
        String birthday = scanner.next();
        String sql="insert into customers(name,email,birth) values(?,?,?)";
        int update = update(sql, name, email, birthday);
        if (update>0){
            System.out.println("添加成功");
        }else{
            System.out.println("添加失败");
        }

    }
    //通用的增删改操作
    //sql中占位符的个数与可变形参的长度相同
    public static int update(String sql, Object... args) {
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
          //  preparedStatement.execute();
            return preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally
        {
            //5.资源的关闭
            JDBCUtils.closeResouse(connection, preparedStatement);

        }
        return 0;

    }

}
