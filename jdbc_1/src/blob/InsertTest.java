package blob;

import org.junit.jupiter.api.Test;
import util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 使用PreparedStatement批量操作操作
 *
 */
public class InsertTest {
    /**
     * 批量插入方式二
     */
    @Test
    public static void main(String[] args) {


        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            long l = System.currentTimeMillis();

            connection = JDBCUtils.getConnection();
            connection.setAutoCommit(false);
            String sql="insert into goods(name) values(?)";
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 1; i < 1000001; i++) {
                preparedStatement.setObject(1,"name_"+i);
                preparedStatement.addBatch();
                if (i%500==0){//500:5215   20000:2882
                    preparedStatement.executeBatch();
                    preparedStatement.clearBatch();

                }

            }
            connection.commit();
            long l1 = System.currentTimeMillis();
            System.out.println(l1-l);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResouse(connection,preparedStatement);
        }


    }
}
