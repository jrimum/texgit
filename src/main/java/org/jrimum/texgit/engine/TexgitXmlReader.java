package org.jrimum.texgit.engine;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;
import static org.jrimum.utilix.Objects.isNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.jrimum.texgit.Texgit;
import org.jrimum.texgit.TexgitException;
import org.jrimum.texgit.language.MetaTexgit;
import org.xml.sax.SAXException;



class TexgitXmlReader {

	public static MetaTexgit parse(File xmlDef) throws TexgitException {

		MetaTexgit txg = null;

		if (isNotNull(xmlDef)) {

			try {

				JAXBContext aJaxbContext = JAXBContext
						.newInstance(MetaTexgit.class);

				Unmarshaller aUnmarshaller = aJaxbContext.createUnmarshaller();

				SchemaFactory aSchemaFactory = SchemaFactory
						.newInstance(W3C_XML_SCHEMA_NS_URI);
				
				Schema schema = aSchemaFactory.newSchema(Texgit.class
						.getResource("/resource/TexgitSchema.xsd"));

				aUnmarshaller.setSchema(schema);

				aUnmarshaller.setEventHandler(new TexgitSchemaValidator());

				txg = (MetaTexgit) aUnmarshaller.unmarshal(new FileInputStream(
						xmlDef));

			} catch (SAXException e) {

				throw new TexgitLanguageException(e);

			} catch (JAXBException e) {

				throw new TexgitLanguageException(e);

			} catch (FileNotFoundException e) {

				throw new TexgitLanguageException(e);
			}
		}

		return txg;
	}

}
