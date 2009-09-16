package br.com.nordestefomento.jrimum.texgit.type.component;

import br.com.nordestefomento.jrimum.utilix.StringUtil;


public enum EnumCommonFiller {

	/**
	 * Filler padrão para preenchimento com zeros a esquerda.
	 */
	ZERO_LEFT(new Filler<Integer>(0, EnumSide.LEFT)),
	
	/**
	 * Filler padrão para preenchimento com zeros a direita.
	 */
	ZERO_RIGHT(new Filler<Integer>(0, EnumSide.RIGHT)),
	
	/**
	 * Filler padrão para preenchimento com espaços em branco a esquerda.
	 */
	WHITE_SPACE_LEFT(new Filler<String>(StringUtil.WHITE_SPACE, EnumSide.LEFT)),
	
	/**
	 * Filler padrão para preenchimento com espaços em branco a direita.
	 */
	WHITE_SPACE_RIGHT(new Filler<String>(StringUtil.WHITE_SPACE, EnumSide.RIGHT));
	
	private Filler<?> instanceEnumFiller;
	
	EnumCommonFiller(Filler<?> filler){
		
		this.instanceEnumFiller = filler;
	}
	
	public Filler<?> getOne(){
		return this.instanceEnumFiller;
	}
}
