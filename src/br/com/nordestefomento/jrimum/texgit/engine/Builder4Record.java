package br.com.nordestefomento.jrimum.texgit.engine;

import static br.com.nordestefomento.jrimum.ACurbitaObject.isNotNull;
import static br.com.nordestefomento.jrimum.ACurbitaObject.isNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import br.com.nordestefomento.jrimum.texgit.language.MetaField;
import br.com.nordestefomento.jrimum.texgit.language.MetaOrderedField;
import br.com.nordestefomento.jrimum.texgit.language.MetaRecord;
import br.com.nordestefomento.jrimum.texgit.type.component.FixedField;
import br.com.nordestefomento.jrimum.texgit.type.component.Record;


class Builder4Record {

	@SuppressWarnings("unchecked")
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
				
		Record record = new Record(strLength,fldSize);
		
		record.setName(metaRecord.getName());
		record.setDescription(metaRecord.getDescription());
		
		/*
		 * getPossition eh de 1 a X
		 * e nao de 0 a X.
		 */ 
		if(isNotNull(id)){
			record.setIdType((FixedField<String>) Builder4FixedField.build(id));
			record.set(id.getPosition()-1, record.getIdType());
		}
		
		if(isNotNull(sequencialNumber)){
			record.setSequencialNumber((FixedField<Long>) Builder4FixedField.build(sequencialNumber));
			record.set(sequencialNumber.getPosition()-1,record.getSequencialNumber());
		}
		
		List<MetaField> fields = metaRecord.getGroupOfFields().getFields();
		
		/*
		 * As somas sao para caso id ou sequencia jah
		 * estejam na devida posicao.
		 */ 
		for(MetaField mField : fields){
			
			if(isNull(record.get(fields.indexOf(mField))))
				record.set(fields.indexOf(mField), Builder4FixedField.build(mField));
			else
				if(isNull(record.get(fields.indexOf(mField)+1)))
					record.set(fields.indexOf(mField) + 1, Builder4FixedField.build(mField));
				else
					record.set(fields.indexOf(mField) + 2, Builder4FixedField.build(mField));
		}

		// innerRecords
		if (isNotNull(metaRecord.getGroupOfInnerRecords())){
			
			record.setHeadOfGroup(true);
			record.setDeclaredInnerRecords(new ArrayList<String>(metaRecord.getGroupOfInnerRecords().getRecords().size()));
			record.setRepitablesRecords(new HashSet<String>());
			
			List<MetaRecord> metaInnerRecords = metaRecord.getGroupOfInnerRecords().getRecords();
			
			for(MetaRecord mRecord : metaInnerRecords){
				record.getDeclaredInnerRecords().add(mRecord.getName());
				if(mRecord.isRepeatable())
					record.getRepitablesRecords().add(mRecord.getName());
			}
			
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
