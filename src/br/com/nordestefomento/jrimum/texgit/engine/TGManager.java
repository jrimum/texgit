package br.com.nordestefomento.jrimum.texgit.engine;

import java.io.File;

import br.com.nordestefomento.jrimum.texgit.IFlatFile;
import br.com.nordestefomento.jrimum.texgit.IRecord;
import br.com.nordestefomento.jrimum.texgit.TexgitException;
import br.com.nordestefomento.jrimum.texgit.language.MetaTexgit;
import br.com.nordestefomento.jrimum.texgit.type.component.FlatFile;


public class TGManager {

	public static IFlatFile<IRecord> buildFlatFile(File xmlDef) {

		IFlatFile<IRecord> iFlatFile = null;

		try {

			MetaTexgit tgMeta = TGXMLReader.parse(xmlDef);

			FlatFile ff = Builder4FlatFile.build(tgMeta.getFlatFile());
			
			iFlatFile = ff;

		} catch (Exception e) {
			throw new TexgitException(e);
		}

		return iFlatFile;
	}

}