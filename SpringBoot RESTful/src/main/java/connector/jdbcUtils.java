package connector;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.apache.commons.pool2.impl.BaseObjectPoolConfig;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 工具类
 */
public final class jdbcUtils {
    /**
     * 连接池类对象
     */
    private static DataSource dataSource;
    private jdbcUtils() {
    }

    /**
     * 注册驱动
     */
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 利用Properties集合加载数据源
            Properties prop = new Properties();
            InputStream is = jdbcUtils.class.getClassLoader().getResourceAsStream("dbcp.properties");
            prop.load(is);
            // 利用工厂模式创建数据库
            dataSource =BasicDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * 建立连接
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConnect() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * 释放资源
     *
     * @param rs
     * @param st
     * @param conn
     */
    public static void free(ResultSet rs, Statement st, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
