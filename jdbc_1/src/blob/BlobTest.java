package blob;

import bean.Customer;
import org.junit.jupiter.api.Test;
import util.JDBCUtils;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

/**
 * 使用PreparedStatement操作blob数据
 */
public class BlobTest {
    //向数据表中取出blob数据
    @Test
    public void testQuery(){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        InputStream inputStream=null;
        FileOutputStream fileOutputStream=null;
        try {
            connection = JDBCUtils.getConnection();
            String sql="select id,name,email,birth,photo from customers where id=?";
             preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1,21);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){

                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                Date date = resultSet.getDate(4);
                Customer customer = new Customer(id, name, email, date);
                System.out.println(customer);
                Blob photo = resultSet.getBlob("photo");
                 inputStream = photo.getBinaryStream();
                 fileOutputStream = new FileOutputStream(new File("张猿猴.jpg"));
                byte[] bytes = new byte[1024];
                int len;
                while ((len=inputStream.read(bytes))!=-1){
                    fileOutputStream.write(bytes,0,len);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
          JDBCUtils.closeResouse(connection,preparedStatement);
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }
//向数据表插入 blob数据
    @Test
    public void testInsert() throws Exception {
        Connection connection = JDBCUtils.getConnection();
       String sql="insert into customers(name,email,birth,photo) values (?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1,"张与好");
        preparedStatement.setObject(2,"zhang@123qq.com");
        preparedStatement.setObject(3,"1992-08-04");
        FileInputStream fileInputStream = new FileInputStream(new File("girl.jpg"));
        preparedStatement.setBlob(4,fileInputStream);
        preparedStatement.execute();
        JDBCUtils.closeResouse(connection,preparedStatement);
        fileInputStream.close();


    }
}
