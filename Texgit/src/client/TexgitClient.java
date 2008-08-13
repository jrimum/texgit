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

		//testWrite();

		testRead();
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
		bodyLancamento.setValue("ConteudoDoHistorico", "Lancamento de teste: UM DEBITO PARA UM CREDITO.");
		bodyLancamento.setValue("CodigoDaLoja", 14);
		
		headerLancamento.addInnerRecord(bodyLancamento);
		
		IRecord headerLancamento2 = ff.createRecord("Detalhe-Header-Lancamento");
		headerLancamento2.setValue("TipoDeLancamento", 'V');
		headerLancamento2.setValue("DataDoLancamento", new Date());
		headerLancamento2.setValue("Usuario", "Gilmar P.S.L.");
		
		IRecord bodyLancamento2 = ff.createRecord("Detalhe-Body-Lancamento");
		bodyLancamento2.setValue("ContaDebito", 131);
		bodyLancamento2.setValue("ContaCredito", 68);
		bodyLancamento2.setValue("Valor", new BigDecimal(41.50));
		bodyLancamento2.setValue("CodigoDoHistorico", 11);
		bodyLancamento2.setValue("ConteudoDoHistorico", "Lancamento de teste 1: VARIOS CREDITOS PARA VARIOS DEBITOS.");
		bodyLancamento2.setValue("CodigoDaLoja", 14);
		
		IRecord bodyLancamento3 = ff.createRecord("Detalhe-Body-Lancamento");
		bodyLancamento3.setValue("ContaDebito", 131);
		bodyLancamento3.setValue("ContaCredito", 68);
		bodyLancamento3.setValue("Valor", new BigDecimal(41.50));
		bodyLancamento3.setValue("CodigoDoHistorico", 11);
		bodyLancamento3.setValue("ConteudoDoHistorico", "Lancamento de teste 2: VARIOS CREDITOS PARA VARIOS DEBITOS.");
		bodyLancamento3.setValue("CodigoDaLoja", 14);
		
		IRecord bodyLancamento4 = ff.createRecord("Detalhe-Body-Lancamento");
		bodyLancamento4.setValue("ContaDebito", 131);
		bodyLancamento4.setValue("ContaCredito", 68);
		bodyLancamento4.setValue("Valor", new BigDecimal(41.50));
		bodyLancamento4.setValue("CodigoDoHistorico", 11);
		bodyLancamento4.setValue("ConteudoDoHistorico", "Lancamento de teste 3: VARIOS CREDITOS PARA VARIOS DEBITOS.");
		bodyLancamento4.setValue("CodigoDaLoja", 14);
		
		IRecord bodyLancamento5 = ff.createRecord("Detalhe-Body-Lancamento");
		bodyLancamento5.setValue("ContaDebito", 131);
		bodyLancamento5.setValue("ContaCredito", 68);
		bodyLancamento5.setValue("Valor", new BigDecimal(41.50));
		bodyLancamento5.setValue("CodigoDoHistorico", 11);
		bodyLancamento5.setValue("ConteudoDoHistorico", "Lancamento de teste 4: VARIOS CREDITOS PARA VARIOS DEBITOS.");
		bodyLancamento5.setValue("CodigoDaLoja", 14);
		
		IRecord bodyLancamento6 = ff.createRecord("Detalhe-Body-Lancamento");
		bodyLancamento6.setValue("ContaDebito", 131);
		bodyLancamento6.setValue("ContaCredito", 68);
		bodyLancamento6.setValue("Valor", new BigDecimal(41.50));
		bodyLancamento6.setValue("CodigoDoHistorico", 11);
		bodyLancamento6.setValue("ConteudoDoHistorico", "Lancamento de teste 5: VARIOS CREDITOS PARA VARIOS DEBITOS.");
		bodyLancamento6.setValue("CodigoDaLoja", 14);
		
		headerLancamento2.addInnerRecord(bodyLancamento2);
		headerLancamento2.addInnerRecord(bodyLancamento3);
		headerLancamento2.addInnerRecord(bodyLancamento4);
		headerLancamento2.addInnerRecord(bodyLancamento5);
		headerLancamento2.addInnerRecord(bodyLancamento6);
		
		IRecord trailler = ff.createRecord("Trailler");
			
		ff.addRecord(header);
		ff.addRecord(headerLancamento);
		ff.addRecord(headerLancamento2);
		ff.addRecord(trailler);
		
		FileUtil.createFile("Teste.txt", ff.write());
	}

	private static void testRead(){
		
		IFlatFile<IRecord> ff = Texgit.createFlatFile(new File("sys_dominio_contabilidade.xml"));
		
		ff.read(FileUtil.read("Teste.txt"));
		
		IRecord header = ff.getRecord("Header");
		
		System.out.println("Empresa: "+header.getValue("CodigoDaEmpresa"));
		
		Collection<IRecord> lancamentos = ff.getRecords("Detalhe-Header-Lancamento");
		
		for(IRecord lancamento : lancamentos){
			
			System.out.println("Data do Lançamento: "+lancamento.getValue("DataDoLancamento"));
			System.out.println("Tipo de Lançamento: "+lancamento.getValue("TipoDeLancamento"));
			System.out.println("Usuário: "+lancamento.getValue("Usuario"));
			
			System.out.println("|");
			System.out.println("-->");
			
			Collection<IRecord> detalhesLancados = lancamento.getInnerRecords();
			
			for(IRecord detalhe : detalhesLancados){
				
				System.out.println("---Debito: "+detalhe.getValue("ContaDebito"));
				System.out.println("---Credito: "+detalhe.getValue("ContaCredito"));
				System.out.println("---Valor: "+detalhe.getValue("Valor"));
				System.out.println("---Cod Hist: "+detalhe.getValue("CodigoDoHistorico"));
				System.out.println("---Historico: "+detalhe.getValue("ConteudoDoHistorico"));
				System.out.println("---Loja: "+detalhe.getValue("CodigoDaLoja"));
			}
			System.out.println("_______________________________________________");
		}
		
	}

}
