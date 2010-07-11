package org.jrimum.texgit.type;

public interface Fixed {

	
	/**
	 * <p>
	 * Informa a validade para a fixação de uma ou mais medidas. Exemplo:
	 * <br />
	 * <pre>
	 * {
	 * isLenghtAsDefine();
	 * isSizeAsDefine();
	 * }
	 * </pre>
	 * </p>
	 * 
	 * @return
	 * 
	 * @throws IllegalStateException
	 */
		
	public abstract boolean isFixedAsDefined() throws IllegalStateException;
}
