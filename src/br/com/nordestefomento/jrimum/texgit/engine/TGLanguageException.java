package br.com.nordestefomento.jrimum.texgit.engine;

import br.com.nordestefomento.jrimum.texgit.TexgitException;

@SuppressWarnings("serial")
public class TGLanguageException extends TexgitException {

	/**
	 * 
	 */
	public TGLanguageException() {
		super();
		
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public TGLanguageException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		
	}

	/**
	 * @param arg0
	 */
	public TGLanguageException(String arg0) {
		super(arg0);
		
	}

	/**
	 * @param arg0
	 */
	public TGLanguageException(Throwable arg0) {
		super(arg0);
		
	}

}
