package texgit.type.component;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import texgit.IFlatFile;
import texgit.ITextFileStream;

import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;

import br.com.nordestefomento.jrimum.utilix.IReadWriteStream;

import static br.com.nordestefomento.jrimum.ACurbitaObject.*;
import static org.apache.commons.lang.StringUtils.*;

@SuppressWarnings("serial")
public class FlatFile implements IFlatFile{

	private List<Record> records;
	
	private Set<String> repitables;
	
	private List<String> writtenOrder;
	
	private Factory4Records factory;

	@SuppressWarnings("unused")
	private FlatFile() {
	}
	
	/**
	 * @param repitables
	 * @param writtenOrder
	 * @param factory
	 */
	public FlatFile(Set<String> repitables, List<String> writtenOrder,
			Factory4Records factory) {
		super();
		this.repitables = repitables;
		this.writtenOrder = writtenOrder;
		this.factory = factory;
		this.records = new ArrayList<Record>();
	}

	public Record getRecord(String idType){
		
		Record typeRecord = null;

		if (isNotBlank(idType)) {
			if (!isRepitable(idType)){	
				if (!records.isEmpty()) {
					for (Record rec : records) {
						if (idType.equals(rec.getIdType().getValue()))
							typeRecord = rec;
					}
				}
			}
		}

		return typeRecord;
	}
	
	public Set<Record> getRecords(String idType) {

		Set<Record> typeRecords = new HashSet<Record>();

		if (isNotBlank(idType)) {
			if (isRepitable(idType)) {
				if (!records.isEmpty()) {
					for (Record rec : records) {
						if (idType.equals(rec.getIdType().getValue()))
							typeRecords.add(rec);
					}
				}
			}
		}

		return typeRecords;
	}
	
	public boolean isRepitable(String idType){
		
		return (isNotNull(repitables) && !repitables.isEmpty() && repitables.contains(idType));
	}
	
	public Record createRecord(String idType){
		
		return factory.create(idType);
	}
	
	public void addRecord(Record record){
		
		if(isNotNull(record))
			if(isMine(record.getIdType().getValue()))
				records.add(record);
	}
	
	public boolean isMine(String idType){
		boolean is = false;
		
		if (isNotBlank(idType)) {
			if(!writtenOrder.isEmpty())
				if(writtenOrder.contains(idType))
					is = true;
		}
		return is;
	}

	@Override
	public void read(List<String> str) {
		
		if(isNotNull(str)){
			if(!str.isEmpty()){
				for(String s : str){
					if(isNotBlank(s)){
						
						//for()
					}
				}
			}
		}
		
	}

	@Override
	public List<String> write() {
		
		ArrayList<String> out = new ArrayList<String>(records.size());
		
		for(String id : writtenOrder){
			
			if(isRepitable(id)){
				
				for(Record rec : getRecords(id)){
					
					out.add(rec.write());
					
					if(rec.isHeadOfGroup())
						out.addAll(rec.writeInnerRecords());
				}
				
			}else{
				
				Record rec = getRecord(id);
				
				out.add(rec.write());
				
				if(rec.isHeadOfGroup())
					out.addAll(rec.writeInnerRecords());
			}
		}
		
		return out;
	}
	
}
