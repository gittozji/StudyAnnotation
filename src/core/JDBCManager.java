package core;

import annotation.Column;
import annotation.Id;
import annotation.Table;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

/**
 * jdbc 操作
 * Created by imyu on 2017-09-18.
 */
public class JDBCManager {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
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

    private Connection getConnection() {
        try {
            Class.forName(prop.getProperty("jdbc.driver"));
            conn = DriverManager.getConnection(prop.getProperty("jdbc.url"), prop.getProperty("jdbc.username"), prop.getProperty("jdbc.password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    private void close() {
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
            e.printStackTrace(System.err);
        }
    }

    public <T> T get(Class<T> clazz, Object id) {
        T obj = null;
        try {
            List<Object> objects = new ArrayList<>();
            Table aTable = (Table) clazz.getAnnotation(Table.class);
            if (aTable == null) {
                System.out.println(clazz.getName() + "没有被@Table注解");
                return null;
            }
            //
            String statement = "";
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Id aId = field.getAnnotation(Id.class);
                Column aColumn = field.getAnnotation(Column.class);
                if (aId != null) {
                    statement += "select * from " + aTable.name() + " where " + aColumn.name() + " = ?;";
                }
            }
            //
            conn = getConnection();
            stmt = conn.prepareStatement(statement);
            stmt.setObject(1, id);
            rs = stmt.executeQuery();
            //
            Method method = null;
            Column aColumn = null;
            obj = clazz.newInstance();
            if (rs.next()) {
                for (Field field : fields) {
                     aColumn = field.getAnnotation(Column.class);
                    if (aColumn != null) {
                        String name = aColumn.name();
                        method = clazz.getDeclaredMethod("set" + firstAlphabetUpper(field.getName()), field.getType());
                        method.invoke(obj, rs.getObject(name, field.getType()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return obj;
    }

    public int insert(Object obj) {
        int count = 0;
        try {
            List<Object> objects = new ArrayList<>();
            Class clazz = obj.getClass();
            Table aTable = (Table) clazz.getAnnotation(Table.class);
            if (aTable == null) {
                System.out.println(clazz.getName() + "没有被@Table注解");
                return 0;
            }
            //
            String statement = "insert into " + aTable.name();
            String str1 = "(";
            String str2 = "values(";
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.getAnnotation(Id.class) != null) {
                    continue;
                }
                Column aColumn1 = field.getAnnotation(Column.class);
                Method method1 = clazz.getDeclaredMethod("get" + firstAlphabetUpper(field.getName()));

                str1 += aColumn1.name() + ", ";
                str2 += "?, ";

                objects.add(method1.invoke(obj));
            }
            str1 = str1.substring(0, str1.lastIndexOf(", "));
            str1 += ") ";
            str2 = str2.substring(0, str2.lastIndexOf(", "));
            str2 += ");";
            statement += str1 + str2;
            //
            count = executeUpdate(statement, objects.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public int update(Object obj) {
        int count = 0;
        try {
            List<Object> objects = new ArrayList<>();
            Class clazz = obj.getClass();
            Table aTable = (Table) clazz.getAnnotation(Table.class);
            if (aTable == null) {
                System.out.println(clazz.getName() + "没有被@Table注解");
                return 0;
            }
            //
            Field[] fields = clazz.getDeclaredFields();
            Id aId = null;
            Column aColumn = null;
            Method method = null;
            for (Field field : fields) {
                if (field.getAnnotation(Id.class) == null) {
                    continue;
                }
                aId = field.getAnnotation(Id.class);
                if (aId != null) {
                    aColumn = field.getAnnotation(Column.class);
                    method = clazz.getDeclaredMethod("get" + firstAlphabetUpper(field.getName()));
                    break;
                }
            }
            //
            String statement = "update " + aTable.name() + " set ";
            for (Field field : fields) {
                Column aColumn1 = field.getAnnotation(Column.class);
                Method method1 = clazz.getDeclaredMethod("get" + firstAlphabetUpper(field.getName()));
                statement += aColumn1.name() + " = ? , ";
                objects.add(method1.invoke(obj));
            }
            if (statement.endsWith(", ")) {
                statement = statement.substring(0, statement.lastIndexOf(","));
            }
            statement += " where " + aColumn.name() + " = ? ;";
            objects.add(method.invoke(obj));
            count = executeUpdate(statement, objects.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public int delete(Object obj) {
        int count = 0;
        try {
            Class clazz = obj.getClass();
            Table aTable = (Table) clazz.getAnnotation(Table.class);
            if (aTable == null) {
                System.out.println(clazz.getName() + "没有被@Table注解");
                return 0;
            }
            //
            Field[] fields = clazz.getDeclaredFields();
            Id aId = null;
            Column aColumn = null;
            Method method = null;
            for (Field field : fields) {
                aId = field.getAnnotation(Id.class);
                if (aId != null) {
                    aColumn = field.getAnnotation(Column.class);
                    method = clazz.getDeclaredMethod("get" + firstAlphabetUpper(field.getName()));
                    break;
                }
            }
            //
            String statement = "delete from " + aTable.name() + " where " + aColumn.name() + " = ? ;";
            Object object = method.invoke(obj);
            count = executeUpdate(statement, object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public List executeQuery(String statement, Object... params) {
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(statement);
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            rs = stmt.executeQuery();
            return resultSetToList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return Collections.emptyList();
    }

    public int executeUpdate(String statement, Object... params) {
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(statement);
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            System.out.println(stmt.toString());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return 0;
    }

    private List resultSetToList(ResultSet rs) throws SQLException {
        List list = new ArrayList();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        while (rs.next()) {
            Map map = new HashMap();
            for (int i = 1; i <= columnCount; i++) {
                map.put(rsmd.getColumnName(i), rs.getObject(i));
            }
            list.add(map);
        }
        return list;
    }

    private String firstAlphabetUpper(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
