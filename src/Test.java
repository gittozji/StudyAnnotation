import core.CreateTableManager;
import core.JDBCManager;
import entity.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 测试类
 * Created by imyu on 2017-09-15.
 */
public class Test {
    public static final void main(String[] args) throws ClassNotFoundException {
        //表语句
        CreateTableManager createTableManager = new CreateTableManager();
        System.out.println("建表语句 " + createTableManager.work());
        //
        JDBCManager manager = new JDBCManager();
        //新增
        User user = new User();
        user.setUsername("0007");
        user.setPassword("password");
        user.setNickname("im宇");
        user.setBirthday("19950211");
        user.setSex("0");
        System.out.println("新增 " + manager.insert(user) + " 条记录");
        //查询
        List<Map> list = manager.executeQuery("select * from user");
        System.out.println("查询 " + list);
        //更新
        System.out.println("更新 " + manager.executeUpdate("update user set vc_password = ?", "1234") + " 条记录");
        //
        user = manager.get(User.class, 1L);
        //更新
        user.setBirthday("20170921");
        System.out.println("更新 " + manager.update(user) + " 条记录");
        //删除
        System.out.println("删除 " + manager.delete(user) + " 条记录");

    }
}
