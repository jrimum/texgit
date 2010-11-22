package org.jrimum.texgit;

import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.jrimum.utilix.Objects.isNotNull;

import java.io.File;

import org.jrimum.texgit.engine.TexgitManager;

public final class Texgit {

	public static final FlatFile<Record> createFlatFile(File xmlDef)
			throws TexgitException {

		if (isNotNull(xmlDef)){
			
			return TexgitManager.buildFlatFile(xmlDef);
		}

		return null;
	}

	public static final FlatFile<Record> createFlatFile(String xmlDef)
			throws TexgitException {

		try {

			if (isNotBlank(xmlDef))
				return createFlatFile(new File(xmlDef));

		} catch (Exception e) {
			throw new TexgitException(e);
		}
		
		return null;
	}
}
