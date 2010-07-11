package org.jrimum.texgit.engine;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;

class TGSchemaValidator implements ValidationEventHandler {

	@Override
	public boolean handleEvent(ValidationEvent event) {

		boolean handled = false;

		if (event.getSeverity() == ValidationEvent.WARNING) {

			System.err.println(getMensagem("ATENÇÃO LAYOUT INCONSISTENTE!\n",
					event));

			handled = true;

		} else
			// ERROR
			System.err.println(getMensagem("ERRO NO LAYOUT!\n", event));

		return handled;
	}

	private String getMensagem(String msg, ValidationEvent event) {

		ValidationEventLocator local = event.getLocator();

		return msg +

		"Linha { " + local.getLineNumber() + " } " + "Conluna [ "
				+ local.getColumnNumber() + " ] " + event.getMessage();
	}
}
