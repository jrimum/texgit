package org.jrimum.texgit.engine;

import org.jrimum.texgit.TexgitException;

@SuppressWarnings("serial")
public class TexgitLanguageException extends TexgitException {

	/**
	 * 
	 */
	public TexgitLanguageException() {
		super();
		
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public TexgitLanguageException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		
	}

	/**
	 * @param arg0
	 */
	public TexgitLanguageException(String arg0) {
		super(arg0);
		
	}

	/**
	 * @param arg0
	 */
	public TexgitLanguageException(Throwable arg0) {
		super(arg0);
		
	}

}
