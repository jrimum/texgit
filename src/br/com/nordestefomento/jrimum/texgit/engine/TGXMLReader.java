package br.com.nordestefomento.jrimum.texgit.engine;

import static br.com.nordestefomento.jrimum.utilix.ObjectUtil.isNotNull;
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import br.com.nordestefomento.jrimum.texgit.Texgit;
import br.com.nordestefomento.jrimum.texgit.TexgitException;
import br.com.nordestefomento.jrimum.texgit.language.MetaTexgit;


class TGXMLReader {

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

				aUnmarshaller.setEventHandler(new TGSchemaValidator());

				txg = (MetaTexgit) aUnmarshaller.unmarshal(new FileInputStream(
						xmlDef));

			} catch (SAXException e) {

				throw new TGLanguageException(e);

			} catch (JAXBException e) {

				throw new TGLanguageException(e);

			} catch (FileNotFoundException e) {

				throw new TGLanguageException(e);
			}
		}

		return txg;
	}

}
