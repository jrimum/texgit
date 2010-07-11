package org.jrimum.texgit.engine;

import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.jrimum.utilix.ObjectUtil.isNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jrimum.texgit.language.MetaRecord;
import org.jrimum.texgit.type.component.Record;
import org.jrimum.texgit.type.component.RecordFactory;



public class Factory4Record implements RecordFactory<Record> {

	private Map<String, MetaRecord> name_record;

	Factory4Record(List<MetaRecord> metaRecords) {

		if (isNotNull(metaRecords)) {
			if (!metaRecords.isEmpty()) {

				name_record = new HashMap<String, MetaRecord>(metaRecords
						.size());

				for (MetaRecord mRecord : metaRecords) {

					name_record.put(mRecord.getName(), mRecord);

					if (isNotNull(mRecord.getGroupOfInnerRecords()))
						loadInnerRecords(name_record, mRecord
								.getGroupOfInnerRecords().getRecords());
				}
			}
		}
	}

	private void loadInnerRecords(Map<String, MetaRecord> name_record,
			List<MetaRecord> innerMetaRecords) {

		if (isNotNull(innerMetaRecords)) {
			if (!innerMetaRecords.isEmpty()) {

				for (MetaRecord iMetaRecord : innerMetaRecords) {

					name_record.put(iMetaRecord.getName(), iMetaRecord);

					if (isNotNull(iMetaRecord.getGroupOfInnerRecords()))
						loadInnerRecords(name_record, iMetaRecord
								.getGroupOfInnerRecords().getRecords());
				}
			}
		}

	}

	public Record create(String name) {

		Record record = null;

		if (isNotBlank(name))
			if (name_record.containsKey(name))
				record = Builder4Record.build(name_record.get(name));

		return record;
	}
}
