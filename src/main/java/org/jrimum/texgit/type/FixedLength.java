package org.jrimum.texgit.type;

public interface FixedLength extends Fixed{
	
	public Integer getFixedLength();
	
	public boolean isTruncate();

	public void setTruncate(boolean truncate);
}
