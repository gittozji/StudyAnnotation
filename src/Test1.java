import core.JDBCManager;
import entity.Buy;
import entity.Product;
import entity.User;

/**
 * 测试类1
 * Created by imyu on 2017-09-22.
 */
public class Test1 {
    public static final void main(String[] args) throws ClassNotFoundException {
        JDBCManager manager = new JDBCManager();
        User user = new User();
        user.setUsername("10000");
        user.setPassword("1234");
        user.setNickname("用户1");
        manager.insert(user);

        Product product = new Product();
        product.setCode("00001");
        product.setName("一号产品");
        product.setPrice(1999.88D);
        manager.insert(product);

        Buy buy = new Buy();
        buy.setUsername("10000");
        buy.setProductCode("00001");
        buy.setDate("20170922");
        manager.insert(buy);
    }
}
