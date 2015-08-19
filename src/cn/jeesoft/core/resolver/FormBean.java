package cn.jeesoft.core.resolver;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数数据绑定注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
@Documented
public @interface FormBean {

	String value() default "";

	/** 是否校验表单重复提交 */
	boolean valid() default false;

	/** 校验表单Token参数 */
	String token() default "ftoken";

	/** 是否使用验证码校验 */
	boolean captcha() default false;
}
