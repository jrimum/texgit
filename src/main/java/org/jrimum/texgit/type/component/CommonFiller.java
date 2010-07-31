package org.jrimum.texgit.type.component;

import org.jrimum.utilix.text.StringUtil;


public enum CommonFiller {

	/**
	 * Filler padrão para preenchimento com zeros a esquerda.
	 */
	ZERO_LEFT(new Filler<Integer>(0, Side.LEFT)),
	
	/**
	 * Filler padrão para preenchimento com zeros a direita.
	 */
	ZERO_RIGHT(new Filler<Integer>(0, Side.RIGHT)),
	
	/**
	 * Filler padrão para preenchimento com espaços em branco a esquerda.
	 */
	WHITE_SPACE_LEFT(new Filler<String>(StringUtil.WHITE_SPACE, Side.LEFT)),
	
	/**
	 * Filler padrão para preenchimento com espaços em branco a direita.
	 */
	WHITE_SPACE_RIGHT(new Filler<String>(StringUtil.WHITE_SPACE, Side.RIGHT));
	
	private Filler<?> instanceEnumFiller;
	
	CommonFiller(Filler<?> filler){
		
		this.instanceEnumFiller = filler;
	}
	
	public Filler<?> getOne(){
		return this.instanceEnumFiller;
	}
}
