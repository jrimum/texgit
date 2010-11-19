package org.jrimum.texgit.type.component;

import static java.lang.String.format;
import static org.jrimum.utilix.Objects.isNotNull;

import org.jrimum.texgit.type.AbstractStringOfFields;
import org.jrimum.texgit.type.FixedLength;
import org.jrimum.texgit.type.FixedSize;
import org.jrimum.utilix.Collections;
import org.jrimum.utilix.Objects;

@SuppressWarnings("serial")
public class BlockOfFields extends AbstractStringOfFields<FixedField<?>> implements FixedSize, FixedLength{

	/**
	 * Definição
	 */
	private Integer length;
	
	/**
	 * Definição
	 */
	private Integer size;
	
	/**
	 * <p>
	 * Tamanho da string de escrita do bloco.
	 * </p>
	 */
	private Integer instantLength; 
	
	/**
	 * 
	 */
	public BlockOfFields() {
		super();
	}

	/**
	 * @param length
	 * @param size
	 */
	public BlockOfFields(Integer length, Integer size) {

		super(size);

		Objects.checkNotNull(length, "length");

		if (length > 0) {

			setLength(length);
			setSize(size);

		} else
			throw new IllegalArgumentException("O comprimento do bloco [ "
					+ length + " ] deve ser um número natural > 0!");
	}
	
	@Override
	public BlockOfFields clone() throws CloneNotSupportedException {
		
		return(BlockOfFields) super.clone();
	}

	@Override
	public void read(String lineOfFields) {

		Objects.checkNotNull(lineOfFields, "String de leitura nula!");

		Objects.checkNotNull(getFields(), "Fields == null");
		Collections.checkNotEmpty(getFields(), "Coleção de fields vazia!");

		if (isSizeAsDefinaed() && isLengthWithDefinaed(lineOfFields.length())) {

			StringBuilder builder = new StringBuilder(lineOfFields);

			for (FixedField<?> field : getFields()) {

				try {

					field.read(builder.substring(0, field.getFixedLength()));
					builder.delete(0, field.getFixedLength());

				} catch (Exception e) {

					throw new IllegalStateException(
							format(
									"Erro ao tentar ler o campo \"%s\" na posição [%s].",
									field.getName(), getFields().indexOf(field)),e);
				}
			}

			builder = null;
		}
	}
	
	@Override
	public String write() {

		Objects.checkNotNull(getFields(), "Fields == null");
		Collections.checkNotEmpty(getFields(), "Coleção de fields vazia!");

		String str = null;

		isSizeAsDefinaed();

		str = super.write();

		instantLength = str.length();

		isFixedAsDefined();

		return str;
	}
	
	public boolean isFixedAsDefined() throws IllegalStateException {
		
		return (isSizeAsDefinaed() && isLengthWithDefinaed());
	}
	
	private boolean isLengthWithDefinaed(){
		
		return isLengthWithDefinaed(instantLength);
	}
	
	private boolean isLengthWithDefinaed(int length){
		
		if(length == getFixedLength())
				return true;
		else
			throw new IllegalStateException("O comprimento da string [ " + instantLength + " ] é incompátivel com o definido ["+getFixedLength()+"]!");
	}
	
	private boolean isSizeAsDefinaed(){
		
		if(size() == getFixedSize())
				return true;
		else
			throw new IllegalStateException("O número de fields [ " + size() + " ] é incompátivel com o definido ["+getFixedSize()+"]!");
	}

	/**
	 * @return the length
	 */
	public Integer getFixedLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	private void setLength(Integer length) {
		
		if (isNotNull(length))
			this.length = length;
		else
			throw new IllegalArgumentException("Comprimento inválido [" + length + "]!");
	}

	/**
	 * @return the size
	 */
	public Integer getFixedSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	private void setSize(Integer size) {
		
		if (isNotNull(size))
			this.size = size;
		else
			throw new IllegalArgumentException("Tamanho inválido [" + size + "]!");
	}
}
