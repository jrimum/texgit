package client;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import texgit.IFlatFile;
import texgit.IRecord;
import texgit.Texgit;
import texgit.util.FileUtil;

public class TexgitClient {


	public static void main(String[] args) {

		testWrite();

		//testRead();
	}
	
	private static void testWrite() {
	
		IFlatFile<IRecord> ff = Texgit.createFlatFile(new File("sys_dominio_contabilidade.xml"));
		
		IRecord header = ff.createRecord("Header");
		header.setValue("CodigoDaEmpresa", 23);
		header.setValue("CPRF", "12345678000123");
		header.setValue("DataInicial", new Date());
		header.setValue("DataFinal", new Date());
		header.setValue("TipoDeNota", 5);
		header.setValue("Sistema", 1);
		
		IRecord headerLancamento = ff.createRecord("Detalhe-Header-Lancamento");
		headerLancamento.setValue("TipoDeLancamento", 'X');
		headerLancamento.setValue("DataDoLancamento", new Date());
		headerLancamento.setValue("Usuario", "Gilmar P.S.L.");
		
		IRecord bodyLancamento = ff.createRecord("Detalhe-Body-Lancamento");
		bodyLancamento.setValue("ContaDebito", 131);
		bodyLancamento.setValue("ContaCredito", 68);
		bodyLancamento.setValue("Valor", new BigDecimal(41.50));
		bodyLancamento.setValue("CodigoDoHistorico", 11);
		bodyLancamento.setValue("ConteudoDoHistorico", "Lançamento de teste: UM DÉBITO PARA UM CRÉDITO.");
		bodyLancamento.setValue("CodigoDaLoja", 14);
		
		headerLancamento.addInnerRecord(bodyLancamento);
		
		IRecord trailler = ff.createRecord("Trailler");
			
		ff.addRecord(header);
		ff.addRecord(headerLancamento);
		ff.addRecord(trailler);
		
		FileUtil.createFile("Teste.txt", ff.write());
	}

	private static void testRead(){
		
		IFlatFile<IRecord> ff = Texgit.createFlatFile(new File("sys_dominio_contabilidade.xml"));
		
		ff.read(FileUtil.read("TO-DO"));
		
		IRecord header = ff.getRecord("Header");
		
		System.out.println("Empresa: "+header.getValue("CodigoDaEmpresa"));
		
		Collection<IRecord> lancamentos = ff.getSameRecords("Detalhe-Header-Lancamento");
		
		for(IRecord lancamento : lancamentos){
			
			System.out.println("Data do Lançamento: "+lancamento.getValue("DataDoLancamento"));
			System.out.println("Tipo de Lançamento: "+lancamento.getValue("TipoDeLancamento"));
			System.out.println("Usuário: "+lancamento.getValue("Usuario"));
			
			System.out.println("|");
			System.out.println("-->");
			
			Collection<IRecord> detalhesLancados = lancamento.getInnerRecords();
			
			for(IRecord detalhe : detalhesLancados){
				
				System.out.println("Debito: "+detalhe.getValue("ContaDebito"));
				System.out.println("Credito: "+detalhe.getValue("ContaCredito"));
				System.out.println("Valor: "+detalhe.getValue("Valor"));
				System.out.println("Cod Hist: "+detalhe.getValue("CodigoDoHistorico"));
				System.out.println("Historico: "+detalhe.getValue("ConteudoDoHistorico"));
				System.out.println("Loja: "+detalhe.getValue("CodigoDaLoja"));
			}
			System.out.println("_______________________________________________");
		}
		
	}

}
