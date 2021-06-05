package practice;

import util.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

public class Exer2 {
    public static void main(String[] args) {
      //  addData();
       // queryInformation();
        delete();
    }
    /**
     * 学生信息的删除
     */
    public static void delete(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入学生的考号");
        String next = scanner.next();
        String sql="delete from examstudent where ExamCard =?";
        int update = update(sql, next);
        if (update==0){
            System.out.println("查无此人");
        }else {
            System.out.println("删除成功");
        }
    }

    /**
     * 根据身份证号或准考证号查询学生成绩
     */
    public static void queryInformation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入你要查询的类型：");
        System.out.println("a.准考证号");
        System.out.println("b.身份证号");
        String selection = scanner.next();
        if ("a".equalsIgnoreCase(selection)){
            System.out.println("请输入准考证号");
            String examCard = scanner.next();
            String sql="select FlowID flowID,Type type,IDCard,ExamCard examCard,StudentName studentName,Location location,Grade grade from examstudent where examCard=?";
            Student instence = getInstence(Student.class, sql, examCard);
            System.out.println(instence);

        }else if("b".equalsIgnoreCase(selection)){
            System.out.println("请输入身份证号");
            String IDCard = scanner.next();
            String sql="select FlowID flowID,Type type,IDCard,ExamCard examCard,StudentName studentName,Location location,Grade grade from examstudent where IDCard=?";
            Student instence = getInstence(Student.class, sql, IDCard);
            System.out.println(instence);

        }else {
            System.out.println("你的输入有误，请重新进入程序");
        }
    }

    /**
     * 插入一条数据
     */
    public static void addData() {
        //添加一条数据
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入四级/六级：");
        int type = scanner.nextInt();
        System.out.println("请输入身份证号：");
        String IDCard = scanner.next();
        System.out.println("请输入准考证号：");
        String examCard = scanner.next();
        System.out.println("请输入学生姓名：");
        String studentName = scanner.next();
        System.out.println("请输入所在城市：");
        String location = scanner.next();
        System.out.println("请输入学生成绩：");
        int grade = scanner.nextInt();
        String sql = "insert into examstudent(type,IDCard,examCard,studentName,location,grade) values(?,?,?,?,?,?)";
        int update = update(sql, type, IDCard, examCard, studentName, location, grade);
        if (update > 0) {
            System.out.println("添加成功");
        } else {
            System.out.println("添加失败");
        }
    }    //通用的增删改操作

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
        } finally {
            //5.资源的关闭
            JDBCUtils.closeResouse(connection, preparedStatement);

        }
        return 0;

    }
    /**
     * 根据身份证号或准考证号查询学生成绩
     *
     */
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
