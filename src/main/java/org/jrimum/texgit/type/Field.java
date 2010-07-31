package org.jrimum.texgit.type;

import java.text.Format;

import org.jrimum.utilix.text.TextStream;

public interface Field<G> extends TextStream, Cloneable{

	public abstract String getName();

	public abstract void setName(String name);

	public abstract G getValue();

	public abstract void setValue(G value);

	public abstract Format getFormatter();

	public abstract void setFormatter(Format formatter);

	public abstract boolean isBlankAccepted();

	public abstract void setBlankAccepted(boolean blankAccepted);
	
	public abstract Field<G> clone() throws CloneNotSupportedException;
}
