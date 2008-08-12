package texgit.type.component;

import static br.com.nordestefomento.jrimum.ACurbitaObject.isNotNull;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import texgit.IFlatFile;
import texgit.IRecord;

@SuppressWarnings("serial")
public class FlatFile implements IFlatFile<IRecord>{

	private List<Record> records;
	
	private Set<String> repitables;
	
	private List<String> writtenOrder;
	
	private IFactory4Record<Record> iFactory4Record;

	public FlatFile(IFactory4Record<Record> iFac4Rec) {
		
		this.iFactory4Record = iFac4Rec;
	}

	public Record getRecord(String idType){
		
		Record typeRecord = null;

		if (isNotBlank(idType)) {
			if (!isRepitable(idType)){	
				if (!records.isEmpty()) {
					for (Record rec : records) {
						if (idType.equals(rec.getIdType().getValue()))
							typeRecord = rec;
					}
				}
			}
		}

		return typeRecord;
	}
	
	public Set<Record> getRecords(String idType) {

		Set<Record> typeRecords = new HashSet<Record>();

		if (isNotBlank(idType)) {
			if (isRepitable(idType)) {
				if (!records.isEmpty()) {
					for (Record rec : records) {
						if (idType.equals(rec.getIdType().getValue()))
							typeRecords.add(rec);
					}
				}
			}
		}

		return typeRecords;
	}
	
	public boolean isRepitable(String idType){
		
		return (isNotNull(repitables) && !repitables.isEmpty() && repitables.contains(idType));
	}
	
	public IRecord createRecord(String idType){
		
		return iFactory4Record.create(idType);
	}
	
	public void addRecord(Record record){
		
		if(isNotNull(record))
			if(isMine(record.getIdType().getValue()))
				records.add(record);
	}
	
	public boolean isMine(String idType){
		boolean is = false;
		
		if (isNotBlank(idType)) {
			if(!writtenOrder.isEmpty())
				if(writtenOrder.contains(idType))
					is = true;
		}
		return is;
	}

	@Override
	public void read(List<String> str) {
		
		if(isNotNull(str)){
			if(!str.isEmpty()){
				for(String s : str){
					if(isNotBlank(s)){
						
						//for()
					}
				}
			}
		}
		
	}

	@Override
	public List<String> write() {
		
		ArrayList<String> out = new ArrayList<String>(records.size());
		
		for(String id : writtenOrder){
			
			if(isRepitable(id)){
				
				for(Record rec : getRecords(id)){
					
					out.add(rec.write());
					
					if(rec.isHeadOfGroup())
						out.addAll(rec.writeInnerRecords());
				}
				
			}else{
				
				Record rec = getRecord(id);
				
				out.add(rec.write());
				
				if(rec.isHeadOfGroup())
					out.addAll(rec.writeInnerRecords());
			}
		}
		
		return out;
	}

	@Override
	public void addAllRecords(Collection<IRecord> records) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addRecord(IRecord record) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addRecords(String idName, Collection<IRecord> records) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<IRecord> getAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IRecord> getSameRecords(String idName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRecord removeRecord(String idName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAllRecords(Collection<IRecord> records) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRecords(Collection<IRecord> records) {
		// TODO Auto-generated method stub
		
	}
	
}
