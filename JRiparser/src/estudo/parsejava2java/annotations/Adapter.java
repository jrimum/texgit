package estudo.parsejava2java.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import estudo.parsejava2java.adapters.AbstractAdapterBase;
import estudo.parsejava2java.adapters.IAdapter;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Adapter {

	Class<? extends IAdapter> adpaterClass() default AbstractAdapterBase.class;
	
	String[] params() default "";
	
	String[] attributesToAdapt() default "";
}
