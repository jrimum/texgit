package texgit.type.component;

import static br.com.nordestefomento.jrimum.ACurbitaObject.isNotNull;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.List;
import java.util.Set;

import texgit.type.IFixedField;

@SuppressWarnings("serial")
public class Record extends BlockOfFields{

	private String name;
	
	private String description;
	
	private FixedField<String> idType;
	
	private FixedField<Long> sequencialNumber;
	
	private boolean headOfGroup;
	
	private List<Record> innerRecords;
	
	private Set<String> declaredInnerRecords;
	
	public Record() {
		super();
	}
	
	@Override
	public Record clone() throws CloneNotSupportedException {
		//TODO 
		return (Record) super.clone();
	}



	@Override
	public void read(String lineRecord) {
		
		//if(record.isThis())
		super.read(lineRecord);
		//else
			//if(isInnerRecord(getId(record)))//EhUmInner
				//someOne.createNewRecord().read(record);
			//else
				//throw new IllegalException();
	}
	
	public FixedField<String> readID(String lineRecord) {

		FixedField<String> ffID = null;
		
		try {
			
			ffID = getIdType().clone();
			ffID.setName("");
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		getIdType().read(
				lineRecord.substring(getIdPosition(), getIdType()
						.getFixedLength()));
		
		return ffID;
	}
	
	public IFixedField<?> getField(String fieldName){
		
		IFixedField<?> field = null;
		
		for(FixedField<?> ff : this.getFields())
			if(ff.getName().equals(fieldName)){
				field = ff;
				break;
			}
		
		return field;
	}

	private int getIdPosition(){
		int pos = 0;
		
		for(FixedField<?> ff : this.getFields())
			if(!ff.getName().equals(idType.getName()))
				pos += ff.getFixedLength();
			else
				break;
		
		return pos;
	}


	@Override
	public String write() {
		
		StringBuilder sb = new StringBuilder(super.write());
		
		if(innerRecords != null && !innerRecords.isEmpty())
			for(Record innRec : innerRecords)
				sb.append(innRec.write()+"\r\n");
		
		return sb.toString();
	}



	public boolean isInnerRecord(String idRecord){
		
		boolean is = false;
		
		if(isNotBlank(idRecord)){
			if(isNotNull(declaredInnerRecords)){
				is = declaredInnerRecords.contains(idRecord.trim());
			}
		}
		
		return is;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public FixedField<String> getIdType() {
		return idType;
	}

	public void setIdType(FixedField<String> idRecord) {
		this.idType = idRecord;
	}

	public FixedField<Long> getSequencialNumber() {
		return sequencialNumber;
	}

	public void setSequencialNumber(FixedField<Long> sequencialNumber) {
		this.sequencialNumber = sequencialNumber;
	}

	public boolean isHeadOfGroup() {
		return headOfGroup;
	}

	public void setHeadOfGroup(boolean headOfSetRecords) {
		this.headOfGroup = headOfSetRecords;
	}

	public List<Record> getInnerRecords() {
		return innerRecords;
	}

	public void setInnerRecords(List<Record> subRecords) {
		this.innerRecords = subRecords;
	}

	public Set<String> getRelated() {
		return declaredInnerRecords;
	}

	public void setRelated(Set<String> related) {
		this.declaredInnerRecords = related;
	}
}