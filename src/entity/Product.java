package entity;

import annotation.Column;
import annotation.Id;
import annotation.Table;

/**
 * 产品
 * Created by imyu on 2017-09-15.
 */
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "id", type = "int(11)")
    Long id;

    @Column(name = "vc_code", type = "varchar(10)", notNull = true, unique = true)
    String code;

    @Column(name = "vc_name", type = "varchar(20)")
    String name;

    @Column(name = "en_price", type = "decimal(19,2)")
    Double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
