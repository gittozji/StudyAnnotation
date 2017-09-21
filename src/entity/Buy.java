package entity;

import annotation.Column;
import annotation.Id;
import annotation.Table;

/**
 * 购买记录
 * Created by imyu on 2017-09-15.
 */
@Table(name = "buy", otherDefinition = "constraint fk_user foreign key(vc_username) references user(vc_username)," +
        "constraint fk_product foreign key(vc_product_code) references product(vc_code)")
public class Buy {
    @Id
    @Column(name = "id", type = "int(11)")
    Long id;

    @Column(name = "vc_date", type = "varchar(8)")
    String date;

    @Column(name = "vc_username", type = "varchar(20)")
    String username;

    @Column(name = "vc_product_code", type = "varchar(10)")
    String productCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
