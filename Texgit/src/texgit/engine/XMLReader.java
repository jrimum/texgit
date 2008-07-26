package texgit.engine;

import static br.com.nordestefomento.jrimum.ACurbitaObject.isNotNull;
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

import texgit.language.MetaTexgit;

public class XMLReader {

	
	public static MetaTexgit parse(File flatFileXML) {

		MetaTexgit txg = null;

		if (isNotNull(flatFileXML)) {

			try {

				JAXBContext aJaxbContext = JAXBContext
						.newInstance(MetaTexgit.class);

				Unmarshaller aUnmarshaller = aJaxbContext.createUnmarshaller();

				SchemaFactory aSchemaFactory = SchemaFactory
						.newInstance(W3C_XML_SCHEMA_NS_URI);

				Schema schema = aSchemaFactory.newSchema(new File(
						"TexgitSchema.xsd"));

				aUnmarshaller.setSchema(schema);

				aUnmarshaller.setEventHandler(new SchemaValidator());

				txg = (MetaTexgit) aUnmarshaller
						.unmarshal(new FileInputStream(flatFileXML));
				
			} catch (SAXException e) {

				e.printStackTrace();
				
			} catch (JAXBException e) {

				e.printStackTrace();
				
			} catch (FileNotFoundException e) {

				e.printStackTrace();
				
			}
		}

		return txg;
	}
	
	public static void main(String[] args) {
		parse(new File("teste.xml"));
	}
}
