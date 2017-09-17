package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段
 * Created by imyu on 2017-09-15.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    // 字段名
    String name();
    // 字段类型
    String type();
    // 是否非空
    boolean notNull() default false;
    // 是否唯一
    boolean unique() default false;
    // 其他定义
    String otherDefinition() default "";
}
