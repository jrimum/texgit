package texgit;

import static br.com.nordestefomento.jrimum.ACurbitaObject.isNotNull;

import java.io.File;

import texgit.engine.Factory4FlatFile;


public final class Texgit {

	public static final IFlatFile getInstance(File xmlLayout)throws TexgitException{
		
		IFlatFile iFlatFile = null;
		
		if (isNotNull(xmlLayout))
			iFlatFile = Factory4FlatFile.create(xmlLayout);
		
		return iFlatFile;
	}
}
