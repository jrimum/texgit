package br.com.nordestefomento.jrimum.texgit.type.component;

import static br.com.nordestefomento.jrimum.utilix.ObjectUtil.isNotNull;
import static br.com.nordestefomento.jrimum.utilix.ObjectUtil.isNull;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@SuppressWarnings("serial")
public class FlatFile implements br.com.nordestefomento.jrimum.texgit.FlatFile<br.com.nordestefomento.jrimum.texgit.Record>{

	private List<Record> records;
	
	private Set<String> repitablesRecords;
	
	private List<String> recordsOrder;
	
	private RecordFactory<Record> recordFactory;

	public FlatFile(RecordFactory<Record> iFac4Rec) {
		
		this.recordFactory = iFac4Rec;
		this.records = new ArrayList<Record>();
	}

	public Record getRecord(String idName){
		
		Record record = null;
		
		if (isNotBlank(idName)) {
			if (!isRepitable(idName)){	
				if (!records.isEmpty()) {
					for (Record rec : records) {
						if (idName.equals(rec.getName()))
							record = rec;
					}
				}
			}
		}

		return record;
	}
	
	public boolean isRepitable(String idName){
		
		return (isNotNull(repitablesRecords) && !repitablesRecords.isEmpty() && repitablesRecords.contains(idName));
	}
	
	public br.com.nordestefomento.jrimum.texgit.Record createRecord(String idName){
		
		return recordFactory.create(idName);
	}
	
	public void addRecord(Record record){
		
		if(isNotNull(record))
			if(isMyRecord(record.getName()))
				records.add(record);
			else
				throw new IllegalArgumentException("Record fora de scopo!");
	}
	
	public boolean isMyRecord(String idName){
		boolean is = false;
		
		if (isNotBlank(idName)) {
			if(!recordsOrder.isEmpty())
				if(recordsOrder.contains(idName))
					is = true;
		}
		return is;
	}

	@Override
	public void read(List<String> str) {
		
		if(isNotNull(str)){
			if(!str.isEmpty()){
		
				boolean read = true;
				
				String line = null;
				int lineIndex = 0;
				
				FixedField<String> typeRecord = null;
				Record record = null;
				
				for(String id : recordsOrder){
					
					record = recordFactory.create(id);
					
					if(isRepitable(id)){
						
						while(read){
							
							if(isNull(record))
								record = recordFactory.create(id);
							
							if(lineIndex < str.size())
								line = str.get(lineIndex);
							
							typeRecord = record.readID(line);
							
							read = record.getIdType().getValue().equals(typeRecord.getValue()) && (lineIndex < str.size()); 

							if(read){
								
								record.read(line);
								lineIndex++;
								addRecord(record);
								
								if(record.isHeadOfGroup())
									lineIndex = record.readInnerRecords(str,lineIndex,recordFactory);
								
								record = null;
							}
						}
						
					}else{
						if((lineIndex < str.size())){
							
							line = str.get(lineIndex);
							typeRecord = record.readID(line);
							
							if(record.getIdType().getValue().equals(typeRecord.getValue())){
								
								record.read(line);
								lineIndex++;
								addRecord(record);
								
								if(record.isHeadOfGroup())
									lineIndex = record.readInnerRecords(str,lineIndex,recordFactory);
								
								record = null;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public List<String> write() {
		
		ArrayList<String> out = new ArrayList<String>(records.size());
		
		for(String id : recordsOrder){
			
			if(isRepitable(id)){
				
				Record rec = null;
				
				for(br.com.nordestefomento.jrimum.texgit.Record record : getRecords(id)){
					
					rec = Record.class.cast(record);
					
					out.add(rec.write()+"\r\n");
					
					if(rec.isHeadOfGroup())
						out.addAll(rec.writeInnerRecords());
				}
				
			}else{
				
				Record rec = getRecord(id);

				out.add(rec.write()+"\r\n");
				
				if(rec.isHeadOfGroup())
					out.addAll(rec.writeInnerRecords());
			}
		}
		
		return out;
	}

	@Override
	public void addRecord(br.com.nordestefomento.jrimum.texgit.Record record) {
		
		if(isNotNull(record)){
			Record rec = Record.class.cast(record);
			addRecord(rec);
		}
	}

	@Override
	public Collection<br.com.nordestefomento.jrimum.texgit.Record> getRecords(String idName) {

		List<br.com.nordestefomento.jrimum.texgit.Record> secRecords = new ArrayList<br.com.nordestefomento.jrimum.texgit.Record>();

		if (isNotBlank(idName)) {
			if (isRepitable(idName)) {
				if (!records.isEmpty()) {
					for (Record rec : records) {
						if (idName.equals(rec.getName()))
							secRecords.add(rec);
					}
				}
			}
		}

		return secRecords;
	}
	
	@Override
	public void addAllRecords(Collection<br.com.nordestefomento.jrimum.texgit.Record> records) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addRecords(String idName, Collection<br.com.nordestefomento.jrimum.texgit.Record> records) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<br.com.nordestefomento.jrimum.texgit.Record> getAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public br.com.nordestefomento.jrimum.texgit.Record removeRecord(String idName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAllRecords(Collection<br.com.nordestefomento.jrimum.texgit.Record> records) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRecords(String idName, Collection<br.com.nordestefomento.jrimum.texgit.Record> records) {
		// TODO Auto-generated method stub
		
	}
	
	public Set<String> getRepitablesRecords() {
		return repitablesRecords;
	}

	public void setRepitablesRecords(Set<String> repitablesRecords) {
		this.repitablesRecords = repitablesRecords;
	}

	public List<String> getRecordsOrder() {
		return recordsOrder;
	}

	public void setRecordsOrder(List<String> recordsOrder) {
		this.recordsOrder = recordsOrder;
	}
	
}
