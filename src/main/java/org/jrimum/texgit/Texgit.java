package org.jrimum.texgit;

import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.jrimum.utilix.Objects.isNotNull;

import java.io.File;

import org.jrimum.texgit.engine.TGManager;




public final class Texgit {

	public static final FlatFile<Record> createFlatFile(File xmlDef)throws TexgitException{
		
		FlatFile<Record> iFlatFile = null;
		
		if (isNotNull(xmlDef))
			iFlatFile = TGManager.buildFlatFile(xmlDef);
		
		return iFlatFile;
	}
	
	public static final FlatFile<Record> createFlatFile(String xmlDef)throws TexgitException{
		
		FlatFile<Record> iFlatFile = null;
		
		try{
		
		if(isNotBlank(xmlDef))	
			iFlatFile = createFlatFile(new File(xmlDef));
	
		}catch (Exception e) {
			throw new TexgitException(e);
		}
		
		return iFlatFile;
	}
}
