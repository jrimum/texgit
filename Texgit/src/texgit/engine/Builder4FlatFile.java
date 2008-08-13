package texgit.engine;

import static br.com.nordestefomento.jrimum.ACurbitaObject.isNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import texgit.language.MetaFlatFile;
import texgit.language.MetaRecord;
import texgit.type.component.FlatFile;

public class Builder4FlatFile {

	static FlatFile build(MetaFlatFile mFlatFile) {
		
		FlatFile ff = null;
		
		List<MetaRecord> metaRecords = mFlatFile.getGroupOfRecords().getRecords();

		ff = new FlatFile(new Factory4Record(metaRecords));
		
		Set<String> repitables = new HashSet<String>();
		
		List<String> recordsOrder = new ArrayList<String>();
		
		if (isNotNull(metaRecords)) {
			
			if (!metaRecords.isEmpty()) {
				
				for (MetaRecord mRec : metaRecords) {
					
					recordsOrder.add(mRec.getName());
					
					if (mRec.isRepeatable())
						repitables.add(mRec.getName());
				}
			}
		}
			
		ff.setRecordsOrder(recordsOrder);
		ff.setRepitablesRecords(repitables);
		
		return ff;
	}
	
}
