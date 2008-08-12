package texgit.engine;

import java.io.File;
import java.util.List;

import texgit.IFlatFile;
import texgit.IRecord;
import texgit.TexgitException;
import texgit.language.MetaRecord;
import texgit.language.MetaTexgit;
import texgit.type.component.FlatFile;

public class TGManager {

	public static IFlatFile<IRecord> buildFlatFile(File xmlDef) {

		IFlatFile<IRecord> iFlatFile = null;

		try {

			MetaTexgit tgMeta = TGXMLReader.parse(xmlDef);

			List<MetaRecord> records = tgMeta.getFlatFile().getGroupOfRecords()
					.getRecords();

			iFlatFile = new FlatFile(new Factory4Record(records));

		} catch (Exception e) {
			throw new TexgitException(e);
		}

		return iFlatFile;
	}

}