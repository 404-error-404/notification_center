package general.notification_center.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 装饰器，用该装饰器装饰的方法无需登录验证
 * @author 小乐乐
 * @date 2022/2/18 21:30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface LoginExcept {
}
