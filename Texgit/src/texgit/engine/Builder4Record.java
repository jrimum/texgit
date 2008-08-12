package texgit.engine;

import static br.com.nordestefomento.jrimum.ACurbitaObject.isNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import texgit.language.MetaField;
import texgit.language.MetaOrderedField;
import texgit.language.MetaRecord;
import texgit.type.component.Record;

class Builder4Record {

	static Record build(MetaRecord metaRecord) {

		int strLength = getStringLength(metaRecord.getGroupOfFields().getFields());
		int fldSize = metaRecord.getGroupOfFields().getFields().size();
		
		MetaOrderedField id = metaRecord.getGroupOfFields().getIdType();
		MetaOrderedField sequencialNumber = metaRecord.getGroupOfFields().getSequencialNumber(); 
		
		if(isNotNull(id)){
			fldSize += 1;
			strLength += id.getLength();
		}
		
		if(isNotNull(sequencialNumber)){
			fldSize += 1;
			strLength += sequencialNumber.getLength();
		}
		
		System.out.println(metaRecord.getName());
		
		System.out.println(fldSize);
		System.out.println(strLength);
		
		Record record = new Record(strLength,fldSize);
		
		record.setName(metaRecord.getName());
		record.setDescription(metaRecord.getDescription());

		if(isNotNull(id))
			record.set(id.getPosition(), Builder4FixedField.build(id));
		
		if(isNotNull(sequencialNumber))
			record.set(sequencialNumber.getPosition(),Builder4FixedField.build(sequencialNumber));
		
		List<MetaField> fields = metaRecord.getGroupOfFields().getFields();
		
		for(MetaField mField : fields)
			record.set(fields.indexOf(mField), Builder4FixedField.build(mField));

		// innerRecords
		if (isNotNull(metaRecord.getGroupOfInnerRecords())){
			
			record.setHeadOfGroup(true);
			record.setDeclaredInnerRecords(new ArrayList<String>(metaRecord.getGroupOfInnerRecords().getRecords().size()));
			
			List<MetaRecord> metaInnerRecords = metaRecord.getGroupOfInnerRecords().getRecords();
			
			for(MetaRecord mRecord : metaInnerRecords)
				record.getDeclaredInnerRecords().add(mRecord.getName());
			
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
