package texgit.engine;

import static br.com.nordestefomento.jrimum.ACurbitaObject.isNotNull;

import java.util.ArrayList;
import java.util.List;

import texgit.language.MetaField;
import texgit.language.MetaOrderedField;
import texgit.language.MetaRecord;
import texgit.type.component.Record;

class Builder4Record {

	static Record build(MetaRecord metaRecord) {

		int strLength = getStringLength(metaRecord.getGroupOfFields().getFields());
		int fldSize = metaRecord.getGroupOfFields().getFields().size();
		
		Record record = new Record(strLength,fldSize);
		
		record.setName(metaRecord.getName());
		record.setDescription(metaRecord.getDescription());

		// fields
		MetaOrderedField id = metaRecord.getGroupOfFields().getIdType();
		MetaOrderedField sequencialNumber = metaRecord.getGroupOfFields().getSequencialNumber(); 
		
		record.set(id.getPosition(), Builder4FixedField.build(id));
		record.set(sequencialNumber.getPosition(), Builder4FixedField.build(sequencialNumber));
		
		List<MetaField> fields = metaRecord.getGroupOfFields().getFields();
		
		for(MetaField mField : fields)
			record.set(fields.indexOf(mField), Builder4FixedField.build(mField));

		// innerRecords
		if (isNotNull(metaRecord.getGroupOfInnerRecords().getRecords())){
			
			record.setHeadOfGroup(true);
			record.setDeclaredInnerRecords(new ArrayList<String>(metaRecord.getGroupOfInnerRecords().getRecords().size()));
			
			List<MetaRecord> metaInnerRecords = metaRecord.getGroupOfInnerRecords().getRecords();
			
			for(MetaRecord mRecord : metaInnerRecords)
				record.getDeclaredInnerRecords().set(metaInnerRecords.indexOf(mRecord),mRecord.getName());
			
		}else
			record.setHeadOfGroup(false);
		
		return record;
	}
	
	private static int getStringLength(List<MetaField> fields){
		int length = 0;
		
		for(MetaField mField : fields)
			length += mField.getLength();
		
		return length;
	}
}
