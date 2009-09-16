package estudo.parsejava2java.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Converter {
	
	String attribute() default "";
	
	Class<?> rootToAdapt() default void.class;
	
	Adapter adapter() default @Adapter;
	
	Adapter[] adapters() default @Adapter;
}
