package texgit.type.component;

import static br.com.nordestefomento.jrimum.ACurbitaObject.isNotNull;

import java.text.Format;

import texgit.type.IFixedField;



@SuppressWarnings("serial")
public class FixedField<G> extends Field<G> implements IFixedField<G>{
	
	/**
	 * <p>
	 * Tamanho de especificação e parâmetro da string de leitura ou escrita do campo.
	 * </p>
	 */
	private Integer length;
	
	/**
	 * <p>
	 * Preenchedor do valor utilizado na hora da escrita.
	 * </p>
	 */
	private Filler<?> filler;
	
	
	/**
	 * <p>
	 * Tamanho da string de escrita do campo.
	 * </p>
	 */
	private Integer instantLength;

	
	/**
	 * 
	 */
	public FixedField() {
		super();
	}
	
	/**
	 * @param ordem
	 * @param length
	 * @param filler
	 */
	public FixedField(String name, G value, Integer length, Filler<?> filler) {
		super(name,value);
		setFixedLength(length);
		setFiller(filler);
	}
	
	/**
	 * @param ordem
	 * @param length
	 * @param filler
	 */
	public FixedField(G value, Format formatter, Integer length, Filler<?> filler) {
		super(value,formatter);
		setFixedLength(length);
		setFiller(filler);
	}
	
	/**
	 * @param ordem
	 * @param length
	 * @param filler
	 */
	public FixedField(String name, G value, Format formatter, Integer length, Filler<?> filler) {
		super(name,value,formatter);
		setFixedLength(length);
		setFiller(filler);
	}

	@SuppressWarnings("unchecked")
	@Override
	public FixedField<G> clone() throws CloneNotSupportedException {
		
		return (FixedField<G>) super.clone();
	}

	/**
	 * @see texgit.type.component.Field#read(java.lang.String)
	 */
	@Override
	public void read(String str) {
		
		if(isNotNull(str,"String Inválida")){
			
			if(str.length() == getFixedLength())
				super.read(str);
			else
				throw new IllegalArgumentException("Tamanho da string [ " + str.length() + " ] diferente do especificado [ "+getFixedLength()+" ]!");
		}
	}

	/**
	 * @see texgit.type.component.Field#write()
	 */
	@Override
	public String write() {
		
		String str = fill(super.write());

		instantLength = str.length();
		
		isFixedAsDefined();
			
		return str;
	}

	private String fill(String str) {
		
		if(isNotNull(filler))
			str = filler.fill(str, length);
		
		return str;
	}
	
	@Override
	public boolean isFixedAsDefined() throws IllegalStateException {
		
		if(instantLength.equals(getFixedLength()))
			return true;
		else
			throw new IllegalStateException("Tamanho da string [ " + instantLength 
					+ " ] diferente do especificado [" + getFixedLength() + "]!");
	}
	
	@Override
	public Integer getFixedLength() {
		
		return this.length;
	}

	public void setFixedLength(Integer length) {
		
		if (isNotNull(length))
			this.length = length;
		else
			throw new IllegalArgumentException("Comprimento inválido [" + length + "]!");
		
	}
	
	@Override
	public Filler<?> getFiller() {
		return filler;
	}

	@Override
	public void setFiller(Filler<?> filler) {
		
		if(isNotNull(filler))
			this.filler = filler;
		else
			throw new IllegalArgumentException("Preenchedor inválido [ " + filler + " ]!");
	}
}
