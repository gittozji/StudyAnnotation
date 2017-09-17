package entity;

import annotation.AutoIncrement;
import annotation.Column;
import annotation.PrimaryKey;
import annotation.Table;

/**
 * 用户表
 * Created by imyu on 2017-09-15.
 */
@Table(name = "user")
public class User {
    @PrimaryKey
    @AutoIncrement
    @Column(name = "id", type = "int(11)")
    Long id;

    @Column(name = "vc_name", type = "varchar(20)", notNull = true, unique = true)
    String username;

    @Column(name = "vc_password", type = "varchar(60)", notNull = true)
    String password;

    @Column(name = "vc_nickname", type = "varchar(20)")
    String nickname;

    @Column(name = "l_birthday", type = "varchar(8)")
    String birthday;

    @Column(name = "c_sex", type = "char(1)", otherDefinition = "default '0'")
    Character sex;
}
