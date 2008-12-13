package estudo.parsejava2java.engine;

import java.lang.reflect.Field;

import ognl.Ognl;
import ognl.OgnlException;
import ognl.OgnlRuntime;
import estudo.parsejava2java.annotations.Converter;
import estudo.parsejava2java.annotations.RootToAdapt;
import estudo.parsejava2java.dominio.to.Endereco;
import estudo.parsejava2java.dominio.to.Logradouro;
import estudo.parsejava2java.dominio.to.Pessoa;

public class AnnotationProcessor {

	private Object from;

	public AnnotationProcessor(Object from) {
		this.from = from;
	}

	public Object process() {

		Class<?> classeFrom = this.from.getClass();
		Object objectRoot = null;

		if (classeFrom.isAnnotationPresent(RootToAdapt.class)) {

			RootToAdapt objectRootAnnotation = classeFrom
					.getAnnotation(RootToAdapt.class);
			Class<?> classeTo = objectRootAnnotation.value();

			try {

				objectRoot = classeTo.newInstance();

				for (Field field : classeFrom.getDeclaredFields()) {

					if (field.isAnnotationPresent(Converter.class)) {
						
						OgnlRuntime.setNullHandler(Pessoa.class, new NullHandlerImpl());
						OgnlRuntime.setNullHandler(Endereco.class, new NullHandlerImpl());
						OgnlRuntime.setNullHandler(Logradouro.class, new NullHandlerImpl());

						field.setAccessible(true);
						
						Converter parseAnnotation = field
								.getAnnotation(Converter.class);

						try {
							Ognl.setValue(parseAnnotation.attribute(),
									objectRoot, field.get(this.from));
						} catch (OgnlException e) {
							e.printStackTrace();
						}
						

					}
				}

			} catch (InstantiationException e) {
				e.printStackTrace();

			} catch (IllegalAccessException e) {
				e.printStackTrace();

			} catch (IllegalArgumentException e) {
				e.printStackTrace();

			}
		}

		return objectRoot;
	}
}
