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
 * Created at: 30/03/2008 - 18:17:00
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
 * Criado em: 30/03/2008 - 18:17:00
 * 
 */
package org.jrimum.texgit.type.component;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.jrimum.utilix.Objects.isNotNull;

import org.apache.commons.lang.StringUtils;
import org.jrimum.texgit.TextStream;
import org.jrimum.utilix.Objects;

/**
 * <p>
 * Preenchedor de caracteres genérico. É utilizado para preencher objetos <code>String</code>,
 * tanto da esquerda para a direita como da direita para esquerda, com o objeto genérico até
 * o tamanho especificado. Caso o tamanho especificado seja <strong>menor</strong> 
 * ou <strong>igual</strong> a 0 (ZERO), este valor será desconsiderado e nada será preenchido.
 * </p>
 * <p>
 * É utilizado o método <code>toString()</code> do objeto preenchedor.
 * </p>
 * <p>
 * Exemplo:<br/>
 * <pre>
 * Filler<Integer> filler = new Filler(new Integer(10), SideToFill.LEFT);
 * String outPut = filler.fill("TESTE", 8);
 * 
 * outPut -> "101TESTE"
 * </pre>
 * </p> 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * @author <a href="http://www.nordestefomento.com.br">Nordeste Fomento
 *         Mercantil</a>
 * 
 * @since JRimum 0.1
 * 
 * @version 0.2.1-inc
 *
 */
@SuppressWarnings("serial")
public class Filler<G> implements org.jrimum.texgit.type.Filler{
	
	private G padding;
	
	private Side sideToFill;
	
	public Filler() {
		super();
	}

	/**
	 * @param fillWith
	 */
	public Filler(G fillWith) {
		
		setPadding(fillWith);
		setSideToFill(Side.LEFT);
	}

	/**
	 * @param fillWith
	 * @param sideToFill
	 */
	public Filler(G fillWith, Side sideToFill) {
		
		setPadding(fillWith);
		setSideToFill(sideToFill);
	}
	
	/**
	 * @see org.jrimum.texgit.type#getPadding()
	 */
	public G getPadding() {
		return padding;
	}

	/**
	 * @see org.jrimum.texgit.type#setPadding(G)
	 */
	public void setPadding(G fillWith) {
		
		if(isNotNull(fillWith)){
			this.padding = fillWith;
		}else{
			throw new IllegalArgumentException(format("Preenchimento inválido [%s]!",fillWith));
		}
	}

	/**
	 * @see org.jrimum.texgit.type#getSideToFill()
	 */
	public Side getSideToFill() {
		return sideToFill;
	}

	/**
	 * @see org.jrimum.texgit.type#setSideToFill(org.jrimum.texgit.type.component.Side)
	 */
	public void setSideToFill(Side sideToFill) {
		
		if(isNotNull(sideToFill)){
			this.sideToFill = sideToFill;
		}else{
			throw new IllegalArgumentException(format("Lado para preenchimento [%s]!",sideToFill));
		}
	}
	
	/**
	 * @see org.jrimum.texgit.type#fill(java.lang.String, int)
	 */
	public String fill(String toFill, int length){
		
		String str = null;
		
		switch(sideToFill){
		
			case LEFT:
				str = fillLeft(toFill, length);
				break;
				
			case RIGHT:
				str = fillRight(toFill, length);
				break;
		}
		
		return str;
	}
	
	/**
	 * @see org.jrimum.texgit.type#fill(long, int)
	 */
	public String fill(long tofill, int length){
		return fill(String.valueOf(tofill), length);
	}
	
	/**
	 * @see org.jrimum.texgit.type#fill(int, int)
	 */
	public String fill(int tofill, int length){
		return fill(String.valueOf(tofill), length);
	}
	
	/**
	 * @see org.jrimum.texgit.type#fill(short, int)
	 */
	public String fill(short tofill, int length){
		return fill(String.valueOf(tofill), length);
	}
	
	/**
	 * @see org.jrimum.texgit.type#fill(byte, int)
	 */
	public String fill(byte tofill, int length){
		return fill(String.valueOf(tofill), length);
	}
	
	/**
	 * @see org.jrimum.texgit.type#fill(char, int)
	 */
	public String fill(char tofill, int length){
		return fill(String.valueOf(tofill), length);
	}
	
	/**
	 * @see org.jrimum.texgit.type#fill(double, int)
	 */
	public String fill(double tofill, int length){
		return fill(String.valueOf(tofill), length);
	}
	
	/**
	 * @see org.jrimum.texgit.type#fill(float, int)
	 */
	public String fill(float tofill, int length){
		return fill(String.valueOf(tofill), length);
	}
	
	/**
	 * @see org.jrimum.texgit.type#fill(java.lang.Object, int)
	 */
	public String fill(Object tofill, int length){
		
		String toFillTemp = null;
		
		if(isNotNull(tofill)){
			toFillTemp = tofill.toString();
		}
		
		return fill(toFillTemp, length);
	}
	
	/**
	 * @see org.jrimum.texgit.type#fill(org.jrimum.texgit.TextStream, int)
	 */
	public String fill(TextStream tofill, int length){

		String toFillTemp = null;
		
		if(isNotNull(tofill)){
			toFillTemp = tofill.write();
		}
		
		return fill(toFillTemp, length);
	}
	
	/**
	 * @param toFill
	 * @param length
	 * @return String preenchida
	 */
	private String fillRight(String toFill, int length) {
		
		return StringUtils.rightPad(toFill, length, padding.toString());
	}

	/**
	 * @param toFill
	 * @param length
	 * @return String preenchida
	 */
	private String fillLeft(String toFill, int length) {
		
		return StringUtils.leftPad(toFill, length, padding.toString());
	}

	@Override
	public String toString() {
		
		return format(
				"Filler [padding=\"%s\", sideToFill=%s]"
				, Objects.whenNull(this.padding, EMPTY)
				, Objects.whenNull(this.sideToFill, EMPTY));
	}
	
}
