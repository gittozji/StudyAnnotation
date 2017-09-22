package entity;

import annotation.Column;
import annotation.Id;
import annotation.Table;

/**
 * 用户表
 * Created by imyu on 2017-09-15.
 */
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id", type = "int(11)")
    Long id;

    @Column(name = "vc_username", type = "varchar(20)", notNull = true, unique = true)
    String username;

    @Column(name = "vc_password", type = "varchar(60)", notNull = true)
    String password;

    @Column(name = "vc_nickname", type = "varchar(20)")
    String nickname;

    @Column(name = "l_birthday", type = "varchar(8)")
    String birthday;

    @Column(name = "c_sex", type = "char(1)", otherDefinition = "default '0'")
    String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", birthday='" + birthday + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
