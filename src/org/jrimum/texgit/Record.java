package org.jrimum.texgit;

import java.util.List;



public interface Record{  
	
	public <G> void setValue(String fieldName, G value);
	
	public <G> G getValue(String fieldName);
	
	public void addInnerRecord(Record record);
	
	public List<Record> getInnerRecords();
}
