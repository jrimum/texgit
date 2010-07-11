package org.jrimum.texgit.type.component;

import static org.jrimum.utilix.ObjectUtil.isNotNull;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNumeric;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import org.jrimum.utilix.DateUtil;
import org.jrimum.utilix.ObjectUtil;
import org.jrimum.utilix.TextStream;

@SuppressWarnings("serial")
public class Field<G> implements org.jrimum.texgit.type.Field<G>{
	
	/**
	 *<p>
	 *Nome do campo, também pode ser usado como id. 
	 * </p>
	 */
	private String name;
	
	/**
	 * <p>
	 * Valor do campo.
	 * </p>
	 */
	private G value;
	
	/**
	 * <p>
	 * Formatador utilizado na leitura e escrita do valor do campo.
	 * </p>
	 */
	private Format formatter;
	
	/**
	 * <p>
	 * Necessário para ler campos númericos em branco.
	 * </p>
	 */
	private boolean blankAccepted;
	
	/**
	 * 
	 */
	public Field() {
		super();
	}

	/**
	 * @param name
	 * @param value
	 */
	public Field(String name, G value) {
		super();
		setName(name);
		setValue(value);
	}

	/**
	 * <p>
	 * Cria um <code>Field</code> com um valor e um formatador para o valor. Isto significa que a leitura e escrita do valor informado
	 * será de acordo com o formatador.
	 * </p>
	 * 
	 * @param value
	 * @param formatter
	 */
	public Field(G value, Format formatter){
		
		setValue(value);
		setFormatter(formatter);
	}
	
	/**
	 * <p>
	 * Cria um <code>Field</code> com nome para identificação, valor e um formatador.
	 * </p>
	 * 
	 * @param name
	 * @param value
	 * @param formatter
	 * 
	 * @see #Field(Object, Format)
	 */
	public Field(String name, G value, Format formatter){
		
		setName(name);
		setValue(value);
		setFormatter(formatter);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Field<G> clone() throws CloneNotSupportedException {
		
		return (Field<G>) super.clone();
	}

	@Override
	public void read(String str) {

		ObjectUtil.checkNotNull(str, "String Inválida");

		if (this.value instanceof TextStream) {

			TextStream reader = (TextStream) this.value;
			reader.read(str);

		} else if (this.value instanceof BigDecimal) {

			readDecimalField(str);

		} else if (this.value instanceof Date) {

			readDateField(str);

		} else if (this.value instanceof Character) {

			readCharacter(str);

		} else {

			readStringOrNumericField(str);
		}
	}

	@SuppressWarnings("unchecked")
	private void readCharacter(String str) {
		
		if(str.length() == 1){
			
			value = (G) new Character(str.charAt(0)); 
			
		}else
			throw new IllegalArgumentException("String com mais de 1 character!");
	}

	@SuppressWarnings("unchecked")
	private void readDecimalField(String str) {
		
		DecimalFormat decimalFormat = (DecimalFormat) formatter;
		
		try {
			
			String number = parseNumber(str);
			
			Long parsedValue = (Long) formatter.parseObject(number);
			
			BigDecimal decimalValue = new BigDecimal(parsedValue.longValue());
			
			decimalValue = decimalValue.movePointLeft(decimalFormat.getMaximumFractionDigits());
							
			value = (G) decimalValue;
			
		} 
		catch (ParseException e) {
			
			errorG(e, str);
		}
	}

	@SuppressWarnings("unchecked")
	private void readDateField(String str) {
		
		try {
			
			if(isBlank(str)){
				
				if(isBlankAccepted())
					value = (G) DateUtil.DATE_NULL;
				else
					new IllegalArgumentException("Campo data vazio não permitido: ["+str+"]");
			}
			
			value = (G) formatter.parseObject(str);
					
		} catch (ParseException e) {
			
			errorG(e, str);
		}
	}

	@SuppressWarnings("unchecked")
	private void readStringOrNumericField(String str) {
		
		str = parseNumber(str);
		
		Class<?> c = value.getClass();

		for (Constructor<?> cons : c.getConstructors()) {

			if (cons.getParameterTypes().length == 1){
				if (cons.getParameterTypes()[0].equals(String.class)){
					try {
						
						value = (G) cons.newInstance(str);

					} catch (IllegalArgumentException e) {
						errorG(e, str).printStackTrace();
					} catch (InstantiationException e) {
						errorG(e, str).printStackTrace();
					} catch (IllegalAccessException e) {
						errorG(e, str).printStackTrace();
					} catch (InvocationTargetException e) {
						errorG(e, str).printStackTrace();
					}
				}
			}
		}
	}
	
	@Override
	public String write() {
		
		String str = null;

		if (value instanceof TextStream) {

			TextStream its = (TextStream) value;

			str = its.write();

		} else if (value instanceof Date) {

			str = writeDateField();
		}

		else if (value instanceof BigDecimal)
			str = writeDecimalField();

		else
			str = value.toString();
		
		return str;
	}
	
	private String writeDecimalField(){
		
		BigDecimal decimalValue = (BigDecimal) value;
		
		decimalValue = decimalValue.movePointRight(((DecimalFormat)formatter).getMaximumFractionDigits());
		
		return decimalValue.toString();
	}
	
	private String writeDateField(){
		
		String str = StringUtils.EMPTY;
		
		Date dateValue = (Date) value;

		if (dateValue.compareTo(DateUtil.DATE_NULL) != 0)
			str = formatter.format(dateValue);
		
		return str;
	}
	
	protected static Exception errorG(Exception e, String value){		
		
		StackTraceElement[] stackTrace = e.getStackTrace();
		e = new RuntimeException("Problems entre a instância e o valor: [ " + value + " ]!\nCausado por: "+e.getCause());
		e.setStackTrace(stackTrace);
		
		return e;
	}
	
	private String parseNumber(String str){
		
		if(isBlank(str)){
			
			if(isBlankAccepted())
				str = "0";
			else
				new IllegalArgumentException("Campo numérico vazio não permitido: ["+str+"]");
		}else
			if(!isNumeric(str))
				new IllegalArgumentException("O campo deve ser numérico e não: ["+str+"]");
		
		return str;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		
		if (isNotNull(name))
			this.name = name;
		else
			throw new IllegalArgumentException("Nome Inválido [" + name + "]!");
	}
	
	public boolean isBlankAccepted() {
		return this.blankAccepted;
	}

	public void setBlankAccepted(boolean blankAccepted) {
		this.blankAccepted = blankAccepted;
	}

	public G getValue() {
		return value;
	}

	public void setValue(G value) {
		
		if (isNotNull(value))
			this.value = value;
		else
			throw new IllegalArgumentException("Valor Inválido [" + value + "]!");
	}

	public Format getFormatter() {
		return formatter;
	}

	public void setFormatter(Format formatter) {
		
		if (isNotNull(formatter))
			this.formatter = formatter;
		else
			throw new IllegalArgumentException("Formato inválido [ " + formatter + " ]!");
	}
}
