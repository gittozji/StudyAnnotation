package core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 * jdbc 操作
 * Created by imyu on 2017-09-18.
 */
public class JDBCManager {
    public Connection conn = null;
    public Statement stmt = null;
    public ResultSet rs = null;
    Properties prop = null;

    public JDBCManager() {
        try {
            InputStream in = getClass().getResourceAsStream("/application.properties");
            prop = new Properties();
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            Class.forName(prop.getProperty("jdbc.driver"));
            conn = DriverManager.getConnection(prop.getProperty("jdbc.url"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void close() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace(System.err); // 输出异常信息
        }
    }

    public int insert(Object obj) {
        return 0;
    }
    public int update(Object obj) {
        return 0;
    }
    public int delete(Object obj) {
        return 0;
    }
    public <E> List<E> select(Class clazz) {
        return null;
    }
}
