package cn.zlg.excel.parser.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.zlg.excel.parser.ExcelOutputType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExcelMapperBean {

	public String file() default "";
	public int startRow() default 0;
	public int endRow() default -1;
	public int sheet() default 0;
	public ExcelOutputType outputType() default ExcelOutputType.LIST;
}