package util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
连接数据库
 */
public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/cash?useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static volatile DataSource DATASOURCE;

    //获取DATASOURCE数据库连接池
    private static DataSource getDATASOURCE() {
        if (DATASOURCE == null) {
            synchronized (DBUtil.class) {
                if (DATASOURCE == null) {
                    DATASOURCE = new MysqlDataSource();
                    ((MysqlDataSource)DATASOURCE).setURL(URL);
                    ((MysqlDataSource)DATASOURCE).setUser(USERNAME);
                    ((MysqlDataSource)DATASOURCE).setPassword(PASSWORD);
                }
            }
        }
        return DATASOURCE;
    }

    //获取连接
    public static Connection getConnection(boolean autocommit) { //便于回滚
        try {
            Connection connection = getDATASOURCE().getConnection();
            connection.setAutoCommit(autocommit);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //关闭资源
    public static void close(Connection connection, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
