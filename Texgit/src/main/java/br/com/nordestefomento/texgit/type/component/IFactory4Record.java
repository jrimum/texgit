package br.com.nordestefomento.texgit.type.component;

import br.com.nordestefomento.texgit.IRecord;

public interface IFactory4Record <G extends IRecord>{

	public abstract G create(String name);
}
