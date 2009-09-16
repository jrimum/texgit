package br.com.nordestefomento.jrimum.texgit.type;

import java.text.Format;

import br.com.nordestefomento.jrimum.utilix.ITextStream;

public interface IField<G> extends ITextStream, Cloneable{

	public abstract String getName();

	public abstract void setName(String name);

	public abstract G getValue();

	public abstract void setValue(G value);

	public abstract Format getFormatter();

	public abstract void setFormatter(Format formatter);

	public abstract boolean isBlankAccepted();

	public abstract void setBlankAccepted(boolean blankAccepted);
	
	public abstract IField<G> clone() throws CloneNotSupportedException;
}
