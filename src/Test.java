import core.CreateTableManager;
import core.JDBCManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by imyu on 2017-09-15.
 */
public class Test {
    public static final void main(String[] args) {
        new JDBCManager();
        CreateTableManager manager = new CreateTableManager();
        try {
            manager.work();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
