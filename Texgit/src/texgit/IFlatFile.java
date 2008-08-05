package texgit;

import java.util.Collection;

public interface IFlatFile extends ITextFileStream{

	//Registros individuais
	
	public IRecord createRecord(String idType);
	
	public void addRecord(IRecord record);
	
	public IRecord getRecord(String idName);
	
	public IRecord removeRecord(String idName);
	
	//Grupos de Registros (Registros que se repetem)
	
	public void addRecords(String idName, Collection<IRecord> records);
	
	public void setRecords(Collection<IRecord> records);
	
	public Collection<IRecord> getSameRecords(String idName);

	//Todos os Registros
	
	public void addAllRecords(Collection<IRecord> records);
	
	public void setAllRecords(Collection<IRecord> records);
	
	public Collection<IRecord> getAllRecords();
	
}
