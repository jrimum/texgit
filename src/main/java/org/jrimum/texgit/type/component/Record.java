/*
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 26/07/2008 - 12:44:41
 * 
 * ================================================================================
 * 
 * Direitos autorais 2008 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 26/07/2008 - 12:44:41
 * 
 */
package org.jrimum.texgit.type.component;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.jrimum.utilix.Objects.isNotNull;
import static org.jrimum.utilix.Objects.isNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jrimum.texgit.type.Field;
import org.jrimum.utilix.Objects;



/**
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 *
 */
@SuppressWarnings("serial")
public class Record extends BlockOfFields implements org.jrimum.texgit.Record{

	private String name;
	
	private String description;
	
	private FixedField<String> idType;
	
	private FixedField<Long> sequencialNumber;
	
	private boolean headOfGroup;
	
	private List<org.jrimum.texgit.Record> innerRecords;
	
	private Set<String> repitablesRecords;
	
	private List<String> declaredInnerRecords;
	
	public Record() {
		super();
	}
	
	/**
	 * @param length
	 * @param size
	 */
	public Record(Integer length, Integer size) {
		super(length, size);
	}

	@Override
	public Record clone() throws CloneNotSupportedException {
		//TODO Outros atributos
		return (Record) super.clone();
	}
	
	@SuppressWarnings("null")
	public FixedField<String> readID(String lineRecord) {

		FixedField<String> ffID = null;
		
		try {
			
			ffID = getIdType().clone();
			ffID.setName("");
			
		} catch (CloneNotSupportedException e) {
			
			throw new UnsupportedOperationException(format("Quebra de contrato [%s] não suporta clonagem!",Objects.whenNull(ffID, "FixedField", ffID.getClass())), e);
		}
		
		getIdType().read(lineRecord.substring(getIdPosition(), getIdPosition() + getIdType().getFixedLength())); 

		return ffID;
	}
	
	public org.jrimum.texgit.type.FixedField<?> getField(String fieldName) {

		org.jrimum.texgit.type.FixedField<?> field = null;

		if (isNotBlank(fieldName))
			if (!getFields().isEmpty())
				for (FixedField<?> ff : this.getFields())
					if (ff.getName().equals(fieldName)) {
						field = ff;
						break;
					}

		return field;
	}

	public boolean isMyField(String idName){
		boolean is = false;
		
		if (isNotBlank(idName)) {
			if(!getFields().isEmpty())
				for(org.jrimum.texgit.type.Field<?> f : getFields())
					if(idName.equals(f.getName())){
						is = true;
						break;
					}
		}
		return is;
	}
	
	private int getIdPosition(){
		int pos = 0;
		
		for(FixedField<?> ff : this.getFields())
			if(!ff.getName().equals(idType.getName()))
				pos += ff.getFixedLength();
			else
				break;
		
		return pos;
	}
	
	public int readInnerRecords(List<String> lines, int lineIndex, RecordFactory<Record> iFactory) {
		
		return readInnerRecords(this,lines,lineIndex,iFactory);
	}
	
	private int readInnerRecords(Record record, List<String> lines, int lineIndex, RecordFactory<Record> iFactory) {
		
		if(isNotNull(record)){
			
			if(isNotNull(record.getDeclaredInnerRecords()) && !record.getDeclaredInnerRecords().isEmpty()){
				
				boolean read = true;
				String line = null;
				
				FixedField<String> typeRecord = null;
				Record innerRec = null;
				
				for(String id : record.getDeclaredInnerRecords()){
					
					innerRec = iFactory.create(id);
					
					try{
						
						if(isRepitable(id)){
							
							while(read){
								
								if(isNull(innerRec))
									innerRec = iFactory.create(id);
								
								if(lineIndex < lines.size())
									line = lines.get(lineIndex);
								
								typeRecord = innerRec.readID(line);
								
								read = innerRec.getIdType().getValue().equals(typeRecord.getValue()) && (lineIndex < lines.size()); 

								if(read){
									
									innerRec.read(line);
									lineIndex++;
									record.addInnerRecord(innerRec);
									
									if(innerRec.isHeadOfGroup())
										innerRec.readInnerRecords(lines,lineIndex,iFactory);
									
									innerRec = null;
								}
							}
							
						}else{
							if((lineIndex < lines.size())){
								
								line = lines.get(lineIndex);
								typeRecord = innerRec.readID(line);
								
								if(innerRec.getIdType().getValue().equals(typeRecord.getValue())){
									
									innerRec.read(line);
									lineIndex++;
									record.addInnerRecord(innerRec);
									
									if(innerRec.isHeadOfGroup())
										innerRec.readInnerRecords(lines,lineIndex,iFactory);
									
									innerRec = null;
								}
							}
						}
						
					} catch (Exception e) {

						throw new IllegalStateException(format(
								"Erro ao tentar ler o registro \"%s\".",
								innerRec.getName()), e);
					}
				}
			}		
		}
		
		return lineIndex;
	}

	public List<String> writeInnerRecords(){
		
		return writeInnerRecords(this,EMPTY);
	}
	
	public List<String> writeInnerRecords(String lineEnding){
		
		return writeInnerRecords(this,lineEnding);
	}
	
	private List<String> writeInnerRecords(Record record, String lineEnding){

		ArrayList<String> out = new ArrayList<String>(record.getInnerRecords().size());
		
		for(String id : getDeclaredInnerRecords()){//ordem
			
			if(isRepitable(id)){
					
				for(Record rec : getRecords(id)){
					
					try{

						out.add(rec.write()+lineEnding);
						
					} catch (Exception e) {

						throw new IllegalStateException(format(
								"Erro ao tentar escrever o registro \"%s\".", rec.getName()), e);
					}
					
					if(rec.isHeadOfGroup())
						out.addAll(rec.writeInnerRecords());
				}
				
			}else{
				
				Record rec = getRecord(id);

				try{
					
					out.add(rec.write()+lineEnding);
					
				} catch (Exception e) {

					throw new IllegalStateException(format(
							"Erro ao tentar escrever o registro \"%s\".", rec.getName()), e);
				}
				
				if(rec.isHeadOfGroup())
					out.addAll(rec.writeInnerRecords());
			}
		}
		
		return out;
	}
	
	public Record getRecord(String idName){
		
		Record record = null;
		
		if (isNotBlank(idName)) {
			if (!isRepitable(idName)){	
				if (!getInnerRecords().isEmpty()) {
					for (org.jrimum.texgit.Record iRec : getInnerRecords()) {
						Record rec = (Record) iRec;
						if (idName.equals(rec.getName()))
							record = rec;
					}
				}
			}
		}

		return record;
	}

	public List<Record> getRecords(String idName) {

		List<Record> secRecords = new ArrayList<Record>();

		if (isNotBlank(idName)) {
			if (isRepitable(idName)) {
				if (!getInnerRecords().isEmpty()) {
					for (org.jrimum.texgit.Record iRec : getInnerRecords()) {
						Record rec = (Record) iRec;
						if (idName.equals(rec.getName()))
							secRecords.add(rec);
					}
				}
			}
		}

		return secRecords;
	}
	
	public boolean isRepitable(String idName){
		
		return (isNotNull(repitablesRecords) && !repitablesRecords.isEmpty() && repitablesRecords.contains(idName));
	}
	
	public boolean isMyRecord(String idName){
		boolean is = false;
		
		if (isNotBlank(idName)) {
			if(!getDeclaredInnerRecords().isEmpty())
				if(getDeclaredInnerRecords().contains(idName))
					is = true;
		}
		return is;
	}
	
	public org.jrimum.texgit.Record addInnerRecord(org.jrimum.texgit.Record record) {
		
		if(isNotNull(record)){
			if(isNull(this.innerRecords))
				this.innerRecords = new ArrayList<org.jrimum.texgit.Record>();
		
		if(isMyRecord(Record.class.cast(record).getName()))
			this.innerRecords.add(record);
		else
			throw new IllegalArgumentException("Record fora de scopo!");
		
		}
		
		return this;
	}

	public List<org.jrimum.texgit.Record> getInnerRecords() {
		
		return this.innerRecords;
	}

	@SuppressWarnings("unchecked")
	public <G> G getValue(String fieldName) {
		
		G value = null;
		
		org.jrimum.texgit.type.Field<?> f = getField(fieldName);
		
		if(isNotNull(f))
			value = (G) f.getValue();
		
		return value;
	}

	@SuppressWarnings("unchecked")
	public <G> org.jrimum.texgit.Record setValue(String fieldName, G value) {
		
		org.jrimum.texgit.type.Field<G> f = (Field<G>) getField(fieldName);
		
		if(isNotNull(f))
			f.setValue(value);
		
		return this;
	}
	
	public boolean hasInnerRecords(){
		return getInnerRecords() != null && !getInnerRecords().isEmpty();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public FixedField<String> getIdType() {
		return idType;
	}

	public void setIdType(FixedField<String> idType) {
		this.idType = idType;
	}

	public FixedField<Long> getSequencialNumber() {
		return sequencialNumber;
	}

	public void setSequencialNumber(FixedField<Long> sequencialNumber) {
		this.sequencialNumber = sequencialNumber;
	}

	public boolean isHeadOfGroup() {
		return headOfGroup;
	}

	public void setHeadOfGroup(boolean headOfGroup) {
		this.headOfGroup = headOfGroup;
	}

	public List<String> getDeclaredInnerRecords() {
		return declaredInnerRecords;
	}

	public void setDeclaredInnerRecords(List<String> declaredInnerRecords) {
		this.declaredInnerRecords = declaredInnerRecords;
	}
	
	public Set<String> getRepitablesRecords() {
		return repitablesRecords;
	}

	public void setRepitablesRecords(Set<String> repitablesRecords) {
		this.repitablesRecords = repitablesRecords;
	}

}