package br.com.nordestefomento.jrimum.texgit.engine;

import java.io.File;

import br.com.nordestefomento.jrimum.texgit.FlatFile;
import br.com.nordestefomento.jrimum.texgit.TexgitException;
import br.com.nordestefomento.jrimum.texgit.language.MetaTexgit;


public class TGManager {

	@SuppressWarnings("unchecked")
	public static br.com.nordestefomento.jrimum.texgit.FlatFile<br.com.nordestefomento.jrimum.texgit.Record> buildFlatFile(File xmlDef) {

		br.com.nordestefomento.jrimum.texgit.FlatFile<br.com.nordestefomento.jrimum.texgit.Record> iFlatFile = null;

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