package org.jrimum.texgit;

import java.util.List;



public interface IRecord{  
	
	public <G> void setValue(String fieldName, G value);
	
	public <G> G getValue(String fieldName);
	
	public void addInnerRecord(IRecord record);
	
	public List<IRecord> getInnerRecords();
}
