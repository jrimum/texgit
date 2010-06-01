package br.com.nordestefomento.jrimum.texgit.type.component;

import br.com.nordestefomento.jrimum.texgit.Record;

public interface RecordFactory <G extends Record>{

	public abstract G create(String name);
}
