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


package br.com.nordestefomento.jrimum.texgit.type.component;

import static br.com.nordestefomento.jrimum.utilix.ObjectUtil.isNotNull;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import br.com.nordestefomento.jrimum.utilix.TextStream;

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
 * @author Misael Barreto
 * @author Rômulo Augusto
 * @author <a href="http://www.nordestefomento.com.br">Nordeste Fomento
 *         Mercantil</a>
 * 
 * @since JRimum 0.1
 * 
 * @version 0.2.1-inc
 *
 */

@SuppressWarnings("serial")
public class Filler<G> implements Serializable{

	private G padding;
	
	private Side sideToFill;
	
	public Filler() {
		super();
	}

	/**
	 * @param padding
	 */
	public Filler(G fillWith) {
		
		setPadding(fillWith);
		setSideToFill(Side.LEFT);
	}

	/**
	 * @param padding
	 * @param sideToFill
	 */
	public Filler(G fillWith, Side sideToFill) {
		
		setPadding(fillWith);
		setSideToFill(sideToFill);
	}
	
	public G getPadding() {
		return padding;
	}

	/**
	 * @param filler
	 */
	public void setPadding(G fillWith) {
		
		if(isNotNull(fillWith))
			this.padding = fillWith;
		
		else
			throw new IllegalArgumentException("Preenchimento inválido [ " + fillWith + " ]!");
	}

	/**
	 * @return
	 */
	public Side getSideToFill() {
		return sideToFill;
	}

	/**
	 * @param sideToFill
	 */
	public void setSideToFill(Side sideToFill) {
		
		if(isNotNull(sideToFill))
			this.sideToFill = sideToFill;
		
		else
			throw new IllegalArgumentException("Lado para preenchimento [ " + sideToFill + " ]!");
	}
	
	/**
	 * <p>
	 * Preenche o campo com o caracter especificado e no lado especificado.
	 * </p>
	 * <p>
	 * Exemplo:
	 * <br/>
	 * Se <code>sideToFill == SideToFill.LEFT</code>, o caracter especificado será adicionado à String
	 * no lado esquerdo até que o campo fique com o tamanho que foi definido.
	 * </p>
	 * 
	 * @param toFill
	 * @param length
	 * @return
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
	 * 
	 * <p>
	 * Executa o método <code>fill(String, int)</code> passando o parâmetro
	 * <code>toFill</code> como <code>String.valueOf(toFill)</code>.
	 * </p>
	 * 
	 * @param tofill
	 * @param length
	 * @return
	 * 
	 * @see Filler#fill(String, int)
	 * 
	 * @since
	 */
	public String fill(long tofill, int length){
		return fill(String.valueOf(tofill), length);
	}
	
	/**
	 * 
	 * <p>
	 * Executa o método <code>fill(String, int)</code> passando o parâmetro
	 * <code>toFill</code> como <code>String.valueOf(toFill)</code>.
	 * </p>
	 * 
	 * @param tofill
	 * @param length
	 * @return
	 * 
	 * @see Filler#fill(String, int)
	 * 
	 * @since
	 */
	public String fill(int tofill, int length){
		return fill(String.valueOf(tofill), length);
	}
	
	/**
	 * 
	 * <p>
	 * Executa o método <code>fill(String, int)</code> passando o parâmetro
	 * <code>toFill</code> como <code>String.valueOf(toFill)</code>.
	 * </p>
	 * 
	 * @param tofill
	 * @param length
	 * @return
	 * 
	 * @see Filler#fill(String, int)
	 * 
	 * @since
	 */
	public String fill(short tofill, int length){
		return fill(String.valueOf(tofill), length);
	}
	
	/**
	 * 
	 * <p>
	 * Executa o método <code>fill(String, int)</code> passando o parâmetro
	 * <code>toFill</code> como <code>String.valueOf(toFill)</code>.
	 * </p>
	 * 
	 * @param tofill
	 * @param length
	 * @return
	 * 
	 * @see Filler#fill(String, int)
	 * 
	 * @since
	 */
	public String fill(byte tofill, int length){
		return fill(String.valueOf(tofill), length);
	}
	
	/**
	 * 
	 * <p>
	 * Executa o método <code>fill(String, int)</code> passando o parâmetro
	 * <code>toFill</code> como <code>String.valueOf(toFill)</code>.
	 * </p>
	 * 
	 * @param tofill
	 * @param length
	 * @return
	 * 
	 * @see Filler#fill(String, int)
	 * 
	 * @since
	 */
	public String fill(char tofill, int length){
		return fill(String.valueOf(tofill), length);
	}
	
	/**
	 * 
	 * <p>
	 * Executa o método <code>fill(String, int)</code> passando o parâmetro
	 * <code>toFill</code> como <code>String.valueOf(toFill)</code>.
	 * </p>
	 * 
	 * @param tofill
	 * @param length
	 * @return
	 * 
	 * @see Filler#fill(String, int)
	 * 
	 * @since
	 */
	public String fill(double tofill, int length){
		return fill(String.valueOf(tofill), length);
	}
	
	/**
	 * 
	 * <p>
	 * Executa o método <code>fill(String, int)</code> passando o parâmetro
	 * <code>toFill</code> como <code>String.valueOf(toFill)</code>.
	 * </p>
	 * 
	 * @param tofill
	 * @param length
	 * @return
	 * 
	 * @see Filler#fill(String, int)
	 * 
	 * @since
	 */
	public String fill(float tofill, int length){
		return fill(String.valueOf(tofill), length);
	}
	
	/**
	 * 
	 * <p>
	 * Executa o método <code>fill(String, int)</code> passando o parâmetro
	 * <code>toFill</code> como <code>toFill.toString()</code>.
	 * <br/>
	 * </p>
	 * <p>
	 * Caso <code>toFill</code> seja <code>null</code>, o método 
	 * <code>fill(String, int)</code> receberá uma String nula como parâmetro.
	 * </p>
	 * 
	 * @param tofill
	 * @param length
	 * @return
	 * 
	 * @see Filler#fill(String, int)
	 * 
	 * @since
	 */
	public String fill(Object tofill, int length){
		
		String toFillTemp = null;
		
		if(isNotNull(tofill))
			toFillTemp = tofill.toString();
		
		return fill(toFillTemp, length);
	}
	
	/**
	 * 
	 * <p>
	 * Executa o método <code>fill(String, int)</code> passando o parâmetro
	 * <code>toFill</code> como <code>toFill.write()</code>.
	 * <br/>
	 * </p>
	 * <p>
	 * Caso <code>toFill</code> seja <code>null</code>, o método 
	 * <code>fill(String, int)</code> receberá uma String nula como parâmetro.
	 * </p>
	 * 
	 * @param tofill
	 * @param length
	 * @return
	 * 
	 * @see Filler#fill(String, int)
	 * 
	 * @since
	 */
	public String fill(TextStream tofill, int length){

		String toFillTemp = null;
		
		if(isNotNull(tofill))
			toFillTemp = tofill.write();
		
		return fill(toFillTemp, length);
	}
	
	/**
	 * @param toFill
	 * @param length
	 * @return
	 */
	private String fillRight(String toFill, int length) {
		
		return StringUtils.rightPad(toFill, length, padding.toString());
	}

	/**
	 * @param toFill
	 * @param length
	 * @return
	 */
	private String fillLeft(String toFill, int length) {
		
		return StringUtils.leftPad(toFill, length, padding.toString());
	}

}
