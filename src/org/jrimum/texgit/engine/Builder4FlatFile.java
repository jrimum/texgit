package org.jrimum.texgit.engine;

import static org.jrimum.utilix.ObjectUtil.isNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jrimum.texgit.language.MetaFlatFile;
import org.jrimum.texgit.language.MetaRecord;
import org.jrimum.texgit.type.component.FlatFile;



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
