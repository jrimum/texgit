package texgit.type.component;

import static br.com.nordestefomento.jrimum.ACurbitaObject.isNotNull;
import texgit.type.AStringOfFields;
import texgit.type.IFixedLength;
import texgit.type.IFixedSize;

@SuppressWarnings("serial")
public class BlockOfFields extends AStringOfFields<FixedField<?>> implements IFixedSize, IFixedLength{

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
		
		if(isNotNull(length, "length")){
			
			if(length > 0){
					
				setLength(length);
				setSize(size);
				
			}else
				throw new IllegalArgumentException("O comprimento do bloco [ " + length + " ] deve ser um número natural > 0!");
		}
	}
	
	@Override
	public BlockOfFields clone() throws CloneNotSupportedException {
		
		return(BlockOfFields) super.clone();
	}

	@Override
	public void read(String lineOfFields){
		
		if(isNotNull(lineOfFields, "lineOfFields")){
			if (isNotNull(getFields(), "fields")) {
				
				if(isSizeAsDefinaed() && isLengthWithDefinaed(lineOfFields.length())){
					
					StringBuilder builder = new StringBuilder(lineOfFields);
					
					for(FixedField<?> field : getFields()){
						
						field.read(builder.substring(0, field.getFixedLength()));
						builder.delete(0, field.getFixedLength());
					}
					
					builder = null;
				}
			}
		}
	}
	
	@Override
	public String write() {

		String str = null;

		if (isNotNull(getFields(), "fields")) {
			
			isSizeAsDefinaed();

			str = super.write();

			instantLength = str.length();

			isFixedAsDefined();
		}
		
		return str;	
	}
	
	@Override
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
