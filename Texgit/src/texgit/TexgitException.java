package texgit;


/**
 * 
 * <p>
 * Invólucro para exceções ocorridas no componente.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com">Gilmar P.S.L.</a>
 * 
 * */
	
@SuppressWarnings("serial")
public class TexgitException extends RuntimeException {

	/**
	 * 
	 */
	public TexgitException() {
		super();
		
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public TexgitException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		
	}

	/**
	 * @param arg0
	 */
	public TexgitException(String arg0) {
		super(arg0);
		
	}

	/**
	 * @param arg0
	 */
	public TexgitException(Throwable arg0) {
		super(arg0);
		
	}

}
