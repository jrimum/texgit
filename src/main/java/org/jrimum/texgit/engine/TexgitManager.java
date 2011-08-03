package org.jrimum.texgit.engine;

import java.io.InputStream;

import org.jrimum.texgit.FlatFile;
import org.jrimum.texgit.Record;
import org.jrimum.texgit.TexgitException;
import org.jrimum.texgit.language.MetaTexgit;

public class TexgitManager {

	public static FlatFile<org.jrimum.texgit.Record> buildFlatFile(InputStream xmlDefStream) {

		FlatFile<Record> iFlatFile = null;

		try {

			MetaTexgit tgMeta = TexgitXmlReader.parse(xmlDefStream);

			iFlatFile = FlatFileBuilder.build(tgMeta.getFlatFile());

		} catch (Exception e) {
			throw new TexgitException(e);
		}

		return iFlatFile;
	}
}