package core;

import annotation.AutoIncrement;
import annotation.Column;
import annotation.PrimaryKey;
import annotation.Table;

import java.io.File;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 负责创建表的管理者
 * Created by imyu on 2017-09-15.
 */
public class CreateTableManager {
    private List<String> getClassNames(String packagePath) {
        List<String> classNames = new ArrayList<>();
        String path = ClassLoader.getSystemResource("").getPath() + packagePath;
        File file = new File(path);
        File[] childFiles = file.listFiles();
        for (File childFile : childFiles) {
            String fileName = childFile.getName();
            classNames.add(packagePath + "." + fileName.substring(0, fileName.lastIndexOf(".")));
        }
        return classNames;
    }

    private List<Class> getClazzs(List<String> classNames) throws ClassNotFoundException {
        List<Class> list = new ArrayList<>();
        Class clazz = null;
        for (String className : classNames) {
            clazz = Class.forName(className);
            if (clazz.getAnnotation(Table.class) != null) {
                list.add(clazz);
            }
        }
        return list;
    }

    private List<String> createSqls(List<Class> src) {
        List<String> list = new ArrayList<>();
        for (Class cls : src) {
            list.add(createSql(cls));
        }
        return list;
    }

    private String createSql(Class cls) {
        Table table = (Table) cls.getAnnotation(Table.class);
        String tableName = table.name();
        String otherDefinition = table.otherDefinition();
        //
        String sql = "create table " + tableName + "(";
        //
        Field[] fields = cls.getDeclaredFields();
        PrimaryKey primaryKey;
        AutoIncrement autoIncrement;
        Column column;
        for (Field field : fields) {
            String primaryKeyStr = "";
            primaryKey = (PrimaryKey) field.getAnnotation(PrimaryKey.class);
            if (primaryKey != null) {
                primaryKeyStr = "primary key ";
            }
            //
            String autoIncrementStr = "";
            autoIncrement = (AutoIncrement) field.getAnnotation(AutoIncrement.class);
            if (autoIncrement != null) {
                autoIncrementStr = "auto_increment ";
            }
            //
            column = (Column) field.getAnnotation(Column.class);
            sql += column.name() + " " + column.type() + " "
                    + (column.notNull() == true ? "not null " : "")
                    + (column.unique() == true ? "unique " : "")
                    + column.otherDefinition()
                    + primaryKeyStr
                    + autoIncrementStr
                    + ",";
        }
        //
        if (otherDefinition.isEmpty()) {
            if (sql.endsWith(",")) {
                sql = sql.substring(0, sql.lastIndexOf(","));
            }
            sql += ");";
        }
        //
        return sql;
    }

    public void work() throws ClassNotFoundException {
        String sql = "";
        List<String> createSqls = createSqls(getClazzs(getClassNames("entity")));
        for (String str : createSqls) {
            sql += str;
        }
        System.out.println(sql);
    }
}
