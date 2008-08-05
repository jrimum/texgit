package texgit;

import java.util.Collection;



public interface IRecord {

	public <G> void setValue(String fieldName, G value);
	
	public <G> G getValue(String fieldName);
	
	public IRecord createInnerRecord(String idType);
	
	public Collection<IRecord> getInnerRecords();
}
