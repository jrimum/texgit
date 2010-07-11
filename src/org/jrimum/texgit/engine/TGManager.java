package org.jrimum.texgit.engine;

import java.io.File;

import org.jrimum.texgit.FlatFile;
import org.jrimum.texgit.TexgitException;
import org.jrimum.texgit.language.MetaTexgit;



public class TGManager {

	@SuppressWarnings("unchecked")
	public static org.jrimum.texgit.FlatFile<org.jrimum.texgit.Record> buildFlatFile(File xmlDef) {

		org.jrimum.texgit.FlatFile<org.jrimum.texgit.Record> iFlatFile = null;

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