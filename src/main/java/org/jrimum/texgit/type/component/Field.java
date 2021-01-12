/*
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 26/07/2008 - 12:44:41
 * 
 * ================================================================================
 * 
 * Direitos autorais 2008 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 26/07/2008 - 12:44:41
 * 
 */
package org.jrimum.texgit.type.component;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNumeric;
import static org.jrimum.utilix.Objects.isNotNull;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.util.Date;

import org.jrimum.texgit.TextStream;
import org.jrimum.utilix.Dates;
import org.jrimum.utilix.Objects;

/**
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 *
 * @param <G>
 */
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
	 * @param value
	 */
	public Field(G value) {
		super();
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

	public void read(String str) {

		Objects.checkNotNull(str, "String inválida [null]!");
		
		try{
			
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
			
		}catch (Exception e) {
			
			throw new IllegalStateException(format("Falha na leitura do campo! %s",toString()),e);
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
			
			throwReadError(e, str);
		}
	}

	@SuppressWarnings("unchecked")
	private void readDateField(String str) {
		
		try {
			
			if(isBlank(str)){
				
				if(isBlankAccepted()){
					
					value = (G) Dates.invalidDate();
					
				}else{
					
					new IllegalArgumentException(format("Campo data vazio não permitido: [%s]!",str));
				}
				
			}else{
				
				value = (G) formatter.parseObject(str);
			}					
			
		} catch (ParseException e) {
			
			throwReadError(e, str);
		}
	}

	@SuppressWarnings("unchecked")
	private void readStringOrNumericField(String str) {
		
		str = parseNumber(str);
		
		Class<?> clazz = value.getClass();
		
		if(clazz.equals(String.class)){
			value = (G) str;
		}else{
			readNumeric(clazz,str);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void readNumeric(Class<?> clazz, String str) {
		
		for (Constructor<?> cons : clazz.getConstructors()) {

			if (cons.getParameterTypes().length == 1){
				
				if (cons.getParameterTypes()[0].equals(String.class)){
					try {
						
						value = (G) cons.newInstance(str);

					} catch (Exception e) {
						
						throwReadError(e, str);
					}
				}
			}
		}
	}

	public String write() {
		
		try{

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
			
		}catch (Exception e) {
			
			throw new IllegalStateException(format("Falha na escrita do campo escrita! %s",toString()),e);
		}
	}
	
	private String writeDecimalField(){
		
		BigDecimal decimalValue = (BigDecimal) value;
		
		decimalValue = decimalValue.movePointRight(((DecimalFormat)formatter).getMaximumFractionDigits());
		
		return decimalValue.toString();
	}
	
	private String writeDateField(){
		
		if (!Dates.equalsInvalidDate((Date) value)){
		
			return formatter.format(value);
		}
		
		return EMPTY;
	}
	
	private String parseNumber(String str){
		
		if(isBlank(str)){
			
			if(isBlankAccepted())
				str = "0";
			else
				new IllegalArgumentException(format("Campo numérico vazio não permitido: [%s]!",str));
		}else
			if(!isNumeric(str))
				new IllegalArgumentException(format("O campo deve ser numérico e não: [%s]!",str));
		
		return str;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		
		if (isNotNull(name))
			this.name = name;
		else
			throw new IllegalArgumentException(format("Nome Inválido: [%s]!",name));
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
			throw new IllegalArgumentException(format("Valor Inválido: [%s]!",value));
	}

	public Format getFormatter() {
		return formatter;
	}

	public void setFormatter(Format formatter) {
		
		if (isNotNull(formatter))
			this.formatter = formatter;
		else
			throw new IllegalArgumentException(format("Formato inválido: [%s]!",formatter));
	}

	private void throwReadError(Exception e, String value){		
		
		throw new IllegalArgumentException(format("Falha na leitura da string: [\"%s\"]! %s",value,toString()), e);
	}
	
	@Override
	public String toString() {
		
		return format("Field [name=\"%s\", value=\"%s\", isBlankAccepted=%s, formatter=%s]"
				, Objects.whenNull(this.name, EMPTY)
				, Objects.whenNull(this.value, EMPTY)
				, Objects.whenNull(this.isBlankAccepted(), EMPTY)
				, Objects.whenNull(this.formatter, EMPTY));
				
	}
}
