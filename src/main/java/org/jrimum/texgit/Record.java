package org.jrimum.texgit;

import java.util.List;

import org.jrimum.utilix.text.TextStream;



public interface Record extends TextStream{  
	
	public <G> Record setValue(String fieldName, G value);
	
	public <G> G getValue(String fieldName);
	
	public Record addInnerRecord(Record record);
	
	public List<Record> getInnerRecords();
}
