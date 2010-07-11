package org.jrimum.texgit.type;

import org.jrimum.texgit.type.component.Filler;


public interface FixedField<G> extends Field<G>, FixedLength{

	/**
	 * @return the filler
	 */
	public abstract Filler<?> getFiller();

	/**
	 * @param filler the filler to set
	 */
	public abstract void setFiller(Filler<?> filler);
}