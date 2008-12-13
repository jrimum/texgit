package estudo.parsejava2java.engine;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;

import ognl.NullHandler;
import ognl.Ognl;

public class NullHandlerImpl implements NullHandler {

	@Override
	public Object nullMethodResult(Map context, Object target,
			String methodName, Object[] args) {
		return null;
	}

	@Override
	public Object nullPropertyValue(Map context, Object target, Object property) {
		Object newInstance = null;
		try {
			Field propertyField = target.getClass().getDeclaredField(
					property.toString());
			Constructor<?>[] construtors = propertyField.getType().getDeclaredConstructors();
			for (Constructor<?> constructor : construtors) {
				Class<?>[] parametros = constructor.getParameterTypes();
				if (parametros.length == 0) {
					constructor.setAccessible(true);
					newInstance = constructor.newInstance();
					break;
				}
			}
			Ognl.setValue(property.toString(), target, newInstance);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newInstance;
	}

}
