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

    @Column(name = "vc_productCode", type = "varchar(10)")
    String productCode;
}
