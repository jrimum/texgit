/*
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 26/07/2008 - 12:44:41
 * 
 * ================================================================================
 * 
 * Direitos autorais 2008 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 26/07/2008 - 12:44:41
 * 
 */
package org.jrimum.texgit;

import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.jrimum.utilix.Objects.isNotNull;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import org.jrimum.texgit.engine.TexgitManager;

/**
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a>
 * 
 */
public final class Texgit {
	
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

	public static final FlatFile<Record> createFlatFile(File xmlDefFile)
			throws TexgitException {

		try {

			if (isNotNull(xmlDefFile)) {
				
				return createFlatFile(new FileInputStream(xmlDefFile));
			}

		} catch (Exception e) {
			throw new TexgitException(e);
		}

		return null;
	}

	public static final FlatFile<Record> createFlatFile(URL xmlDefUrl)
			throws TexgitException {
		
		try {

			if (isNotNull(xmlDefUrl)) {
				
				return TexgitManager.buildFlatFile(xmlDefUrl.openStream());
			}

		} catch (Exception e) {
			throw new TexgitException(e);
		}
		
		
		return null;
	}

	public static final FlatFile<Record> createFlatFile(byte[] xmlDefBytes)
			throws TexgitException {
		
		try {
			
			if (isNotNull(xmlDefBytes)) {
				
				return TexgitManager.buildFlatFile(new ByteArrayInputStream(xmlDefBytes));
			}
			
		} catch (Exception e) {
			throw new TexgitException(e);
		}
		
		return null;
	}
	
	public static final FlatFile<Record> createFlatFile(InputStream xmlDefStream)
			throws TexgitException {

		if (isNotNull(xmlDefStream)) {

			return TexgitManager.buildFlatFile(xmlDefStream);
		}

		return null;
	}

}
