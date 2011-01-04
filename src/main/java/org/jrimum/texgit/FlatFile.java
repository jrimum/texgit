package org.jrimum.texgit;

import java.util.Collection;

public interface FlatFile<G extends Record> extends TextListStream {

	// Registros individuais

	public G createRecord(String idType);

	public FlatFile<G> addRecord(G record);

	public G getRecord(String idName);

	public G removeRecord(String idName);

	// Grupos de Registros (Registros que se repetem)

	public FlatFile<G> addRecords(String idName, Collection<G> records);

	public FlatFile<G> setRecords(String idName, Collection<G> records);

	public Collection<G> getRecords(String idName);

	// Todos os Registros

	public FlatFile<G> addAllRecords(Collection<G> records);

	public FlatFile<G> setAllRecords(Collection<G> records);

	public Collection<G> getAllRecords();

}
