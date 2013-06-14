package cn.zlg.excel.parser.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
public @interface ExcelColumn {

	public int value() default 0;
	public String col() default "";
	public String title() default "";
	public boolean key() default false;
	
}
