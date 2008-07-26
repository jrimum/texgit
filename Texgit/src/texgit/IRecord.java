package texgit;

import texgit.type.IField;


public interface IRecord {

	public IField createField(String name);
	
	public IRecord createInnerRecord(String idType);
}
