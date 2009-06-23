package texgit;

import java.util.Collection;

public interface IFlatFile<G extends IRecord> extends ITextFileStream {

	// Registros individuais

	public G createRecord(String idType);

	public void addRecord(G record);

	public G getRecord(String idName);

	public G removeRecord(String idName);

	// Grupos de Registros (Registros que se repetem)

	public void addRecords(String idName, Collection<G> records);

	public void setRecords(String idName, Collection<G> records);

	public Collection<G> getRecords(String idName);

	// Todos os Registros

	public void addAllRecords(Collection<G> records);

	public void setAllRecords(Collection<G> records);

	public Collection<G> getAllRecords();

}
