package texgit.type.component;

import texgit.IRecord;

public interface IFactory4Record <G extends IRecord>{

	public abstract G create(String name);
}
