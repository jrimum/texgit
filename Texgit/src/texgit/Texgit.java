package texgit;

import static br.com.nordestefomento.jrimum.ACurbitaObject.isNotNull;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.io.File;

import texgit.engine.TGManager;


public final class Texgit {

	public static final IFlatFile createFlatFile(File xmlDef)throws TexgitException{
		
		IFlatFile iFlatFile = null;
		
		if (isNotNull(xmlDef))
			iFlatFile = TGManager.buildFlatFile(xmlDef);
		
		return iFlatFile;
	}
	
	public static final IFlatFile createFlatFile(String xmlDef)throws TexgitException{
		
		IFlatFile iFlatFile = null;
		
		try{
		
		if(isNotBlank(xmlDef))	
			iFlatFile = createFlatFile(new File(xmlDef));
	
		}catch (Exception e) {
			throw new TexgitException(e);
		}
		
		return iFlatFile;
	}
}
