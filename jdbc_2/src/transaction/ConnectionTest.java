package transaction;

import org.junit.jupiter.api.Test;
import util.JDBCUtils;

import java.sql.Connection;

public class ConnectionTest {
    @Test
    public void testGetConnection() throws Exception {
        Connection connection = JDBCUtils.getConnection();
        System.out.println(connection);
    }
}
