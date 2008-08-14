package client;

import static br.com.nordestefomento.jrimum.ACurbitaObject.isNotNull;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collection;

import texgit.IFlatFile;
import texgit.IRecord;
import texgit.Texgit;
import texgit.util.FileUtil;

public class TGCBradesco {

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

		testRead();

	}
	
	private static void testRead(){
		
		IFlatFile<IRecord> ff = Texgit.createFlatFile(new File("bradesco_envio_cnab400.xml"));
		
		ff.read(FileUtil.read("CB0608BE.REM"));
		
		IRecord header = ff.getRecord("Header");
		
		System.out.println("Codigo Empresa: "+header.getValue("CodigoDaEmpresa"));
		System.out.println("Razão Social: "+header.getValue("NomeDaEmpresa"));
		System.out.println("Servico: "+header.getValue("LiteralServico").toString().trim()+"/"+header.getValue("NomeBanco"));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		System.out.println("Data De Gravação Do Arquivo: "+sdf.format(header.getValue("DataGravacaoArquivo")));
		System.out.println("===========================================================================================");
		Collection<IRecord> titulosEmCobranca = ff.getRecords("TransacaoTitulo");
		
		for(IRecord titulo : titulosEmCobranca){
			
			System.out.println("---------------------");
			System.out.println("<<<Dados do Título>>>");
			System.out.println("---------------------");
			
			System.out.println("Nosso Número: "+titulo.getValue("NossoNumero"));
			System.out.println("Numero Do Documento: "+titulo.getValue("NumeroDoDocumento"));
			System.out.println("Vencimento: "+sdf.format(titulo.getValue("Vencimento")));
			System.out.println("Valor: "+titulo.getValue("Valor"));
			
			if(isNotNull(titulo.getInnerRecords())){
				
				Collection<IRecord> dadosParaMensagensBoleto = titulo.getInnerRecords();
				System.out.println("*************************");
				System.out.println("<<<Dados Para Boleto>>>");
				System.out.println("*************************");
				
				for(IRecord dados : dadosParaMensagensBoleto){
					
					System.out.println("Carteira: "+dados.getValue("Carteira"));
					System.out.println("Agencia: "+dados.getValue("Agencia"));
					System.out.println("Conta-Corrente: "+dados.getValue("ContaCorrente")+"-"+dados.getValue("DigitoCC"));
					System.out.println("Mensagem1: "+dados.getValue("Mensagem1"));
					System.out.println("Mensagem2: "+dados.getValue("Mensagem2"));
					System.out.println("Mensagem3: "+dados.getValue("Mensagem3"));
					System.out.println("Mensagem4: "+dados.getValue("Mensagem4"));
				}
			}
			System.out.println("_______________________________________________________________________________________");
		}
		
		
	}

}
