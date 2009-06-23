package br.com.nordestefomento.texgit.client;

import java.io.File;
import java.util.Collection;

import br.com.nordestefomento.texgit.IFlatFile;
import br.com.nordestefomento.texgit.IRecord;
import br.com.nordestefomento.texgit.Texgit;
import br.com.nordestefomento.texgit.type.util.FileUtil;

public class LerArquivoSamuca {

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
		ler();
	}
	
	public static void ler() {
		
		IFlatFile<IRecord> ff = Texgit.createFlatFile(new File("DescritorSamuca.xml"));
		ff.read(FileUtil.read("ArquivoSamuca.txt"));
		
		Collection<IRecord> clientes = ff.getRecords("cliente");
		for (IRecord cliente : clientes) {
			
			long codigo = cliente.getValue("codigo");
			
			System.out.println("Código: " + codigo);
			System.out.println("Nome: " + cliente.getValue("nome"));
			System.out.println("CPF: " + cliente.getValue("cpf"));
			
			if (codigo == 1112) {
				cliente.setValue("nome", "Samuca Meu Carro");
			}
			
		}
		
		FileUtil.createFile("ArquivoSamuca2.txt", ff.write());
	}
	
	public static void escrever() {
	}

}
