package br.com.nordestefomento.jrimum.texgit.type.component;

import br.com.nordestefomento.jrimum.texgit.IRecord;

public interface RecordFactory <G extends IRecord>{

	public abstract G create(String name);
}
