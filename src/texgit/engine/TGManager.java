package texgit.engine;

import java.io.File;

import texgit.IFlatFile;
import texgit.IRecord;
import texgit.TexgitException;
import texgit.language.MetaTexgit;
import texgit.type.component.FlatFile;

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