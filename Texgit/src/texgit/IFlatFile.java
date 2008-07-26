package texgit;

import java.util.List;

public interface IFlatFile {

	public IRecord getRecord(String idType);
	
	public List<IRecord> getRecords(String idType);
	
	public IRecord createRecord(String idType);

}
