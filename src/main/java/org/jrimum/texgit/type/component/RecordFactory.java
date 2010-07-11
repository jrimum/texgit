package org.jrimum.texgit.type.component;

import org.jrimum.texgit.Record;

public interface RecordFactory <G extends Record>{

	public abstract G create(String name);
}
