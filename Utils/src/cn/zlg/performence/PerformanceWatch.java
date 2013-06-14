package cn.zlg.performence;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PerformanceWatch {

	/**
	 * indicate whether this annotated method is the target to call 
	 * @return
	 */
	public boolean value() default false;
}
