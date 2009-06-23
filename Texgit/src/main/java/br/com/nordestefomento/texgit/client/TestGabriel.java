package br.com.nordestefomento.texgit.client;

import java.util.ArrayList;
import java.util.List;

import br.com.nordestefomento.texgit.IFlatFile;
import br.com.nordestefomento.texgit.IRecord;
import br.com.nordestefomento.texgit.Texgit;

public class TestGabriel {

	/**
	 * <p>
	 * SOBRE O MÉTODO
	 * </p>
	 * 
	 * @param args
	 * 
	 * @since 
	 */

	public static void main(String[] args) {

		IFlatFile<IRecord> flatFile = Texgit.createFlatFile("TesteGabriel.xml");
		
		IRecord protocolo = flatFile.createRecord("Protocolo");
		protocolo.setValue("CodigoEstacaoChamadora", "123456");
		flatFile.addRecord(protocolo);
		
		String linha = "B49C123456";
		List<String> lista = new ArrayList<String>();
		lista.add(linha);
		
		flatFile.read(lista);
		IRecord x = flatFile.getRecord("Protocolo");
		
		String id = x.getValue("Id");
		
		System.out.println(id);
	}
	
	

}
