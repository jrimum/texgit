package org.jrimum.texgit;

import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.jrimum.utilix.Objects.isNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.jrimum.texgit.engine.TexgitManager;

public final class Texgit {

	public static final FlatFile<Record> createFlatFile(File xmlDefFile)
			throws TexgitException {

		try {

			if (isNotNull(xmlDefFile))
				return createFlatFile(new FileInputStream(xmlDefFile));

		} catch (Exception e) {
			throw new TexgitException(e);
		}
		
		return null;
	}
	
	public static final FlatFile<Record> createFlatFile(InputStream xmlDefStream)
			throws TexgitException {

		if (isNotNull(xmlDefStream)){
			
			return TexgitManager.buildFlatFile(xmlDefStream);
		}

		return null;
	}

	public static final FlatFile<Record> createFlatFile(String xmlDefFilePath)
			throws TexgitException {

		try {

			if (isNotBlank(xmlDefFilePath))
				return createFlatFile(new File(xmlDefFilePath));

		} catch (Exception e) {
			throw new TexgitException(e);
		}
		
		return null;
	}
}
