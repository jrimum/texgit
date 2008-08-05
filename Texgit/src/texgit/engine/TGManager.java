package texgit.engine;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.io.File;

import texgit.IFlatFile;
import texgit.Texgit;
import texgit.TexgitException;
import texgit.language.MetaFlatFile;
import texgit.language.MetaTexgit;

public class TGManager {

	public static IFlatFile buildFlatFile(File xmlDef){
		
		try{
			
			MetaTexgit tgMeta = TGXMLReader.parse(xmlDef);
			
			MetaFlatFile ffMeta = tgMeta.getFlatFile();
			
			ffMeta.getGroupOfRecords().getRecords();
			
		}catch (Exception e) {
			throw new TexgitException(e);
		}
		
		return null;
	}

}
