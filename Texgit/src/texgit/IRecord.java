package texgit;

import java.util.List;



public interface IRecord{  
	
	public <G> void setValue(String fieldName, G value);
	
	public <G> G getValue(String fieldName);
	
	public IRecord createInnerRecord(String idType);
	
	public List<IRecord> getInnerRecords();
}
