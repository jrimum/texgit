package texgit.engine;

import texgit.IFlatFile;
import texgit.language.MetaTexgit;

import static br.com.nordestefomento.jrimum.ACurbitaObject.*;

public class Factory4FlatFile {

	public static IFlatFile create(MetaTexgit txg) {
		
		if(isNotNull(txg)){
			
			txg.getFlatFile();
		}
		
		return null;
	}

	

}
