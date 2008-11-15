package client;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

import texgit.IFlatFile;
import texgit.IRecord;
import texgit.Texgit;
import texgit.util.FileUtil;


public class TGCTestContabilidadeNFM {

	static final String USUARIO = "Nordeste Fomento Mercantil LTD";
	static final Integer FILIALMATRIZ = 4027;
	
	static int numeroSequencial;
	
	public static void main(String[] args) {
		
		exportarLayoutNovo();
	}
	
	static void exportarLayoutNovo(){
		
		IFlatFile<IRecord> ff = Texgit.createFlatFile(new File("sys_dominio_contabilidade.xml"));
		
		ff.addRecord(createHeader(ff));
		ff.addRecord(lancamentoUmDebitoParaUmCredito(ff));
		ff.addRecord(lancamentoVariosDebitosParaVariosCreditos(ff));
		ff.addRecord(lancamentoUmDebitoParaVariosCreditos(ff));
		ff.addRecord(lancamentoUmCreditoParaVariosDebitos(ff));
		ff.addRecord(ff.createRecord("Trailler"));

		FileUtil.createFile("Teste.txt", ff.write());
	}
	
	static IRecord createHeader(IFlatFile<IRecord> ff){
		
		IRecord header = ff.createRecord("Header");
		
		header.setValue("CodigoDaEmpresa", FILIALMATRIZ);
		header.setValue("CPRF", "03827635000155");
		header.setValue("DataInicial", new Date());
		header.setValue("DataFinal", new Date());
		header.setValue("TipoDeNota", 5);
		header.setValue("Sistema", 1);
		
		return header;
	}
	
	 static IRecord lancamentoUmDebitoParaUmCredito(IFlatFile<IRecord> ff){
		
		IRecord headerLancamento = ff.createRecord("Detalhe-Header-Lancamento");
		headerLancamento.setValue("TipoDeLancamento", 'X');
		headerLancamento.setValue("DataDoLancamento", new Date());
		headerLancamento.setValue("Usuario", USUARIO);
		headerLancamento.setValue("Sequencia", ++numeroSequencial);
		
		IRecord bodyLancamento = ff.createRecord("Detalhe-Body-Lancamento");
		bodyLancamento.setValue("ContaDebito", 5);
		bodyLancamento.setValue("ContaCredito", 313);
		bodyLancamento.setValue("Valor", new BigDecimal(1.50));
		bodyLancamento.setValue("CodigoDoHistorico", 1);
		bodyLancamento.setValue("ConteudoDoHistorico", "Lancamento de teste: UM DEBITO PARA UM CREDITO.");
		bodyLancamento.setValue("CodigoDaLoja", FILIALMATRIZ);
		bodyLancamento.setValue("Sequencia", ++numeroSequencial);
		
		headerLancamento.addInnerRecord(bodyLancamento);
		
		return headerLancamento;
	}
	
	 static IRecord lancamentoVariosDebitosParaVariosCreditos(IFlatFile<IRecord> ff){
		
		 	IRecord headerLancamento = ff.createRecord("Detalhe-Header-Lancamento");
			headerLancamento.setValue("TipoDeLancamento", 'V');
			headerLancamento.setValue("DataDoLancamento", new Date());
			headerLancamento.setValue("Usuario", USUARIO);
			headerLancamento.setValue("Sequencia", ++numeroSequencial);
			
			IRecord debito1 = ff.createRecord("Detalhe-Body-Lancamento");
			debito1.setValue("ContaDebito", 5);
			debito1.setValue("ContaCredito", 0);
			debito1.setValue("Valor", new BigDecimal(1));
			debito1.setValue("CodigoDoHistorico", 1);
			debito1.setValue("ConteudoDoHistorico", "Lancamento de teste VARIOS DEBITOS PARA VARIOS CREDITOS: debito 1");
			debito1.setValue("CodigoDaLoja", FILIALMATRIZ);
			debito1.setValue("Sequencia", ++numeroSequencial);
			
			IRecord debito2 = ff.createRecord("Detalhe-Body-Lancamento");
			debito2.setValue("ContaDebito", 5);
			debito2.setValue("ContaCredito", 0);
			debito2.setValue("Valor", new BigDecimal(2));
			debito2.setValue("CodigoDoHistorico", 1);
			debito2.setValue("ConteudoDoHistorico", "Lancamento de teste VARIOS DEBITOS PARA VARIOS CREDITOS: debito 2");
			debito2.setValue("CodigoDaLoja", FILIALMATRIZ);
			debito2.setValue("Sequencia", ++numeroSequencial);
			
			IRecord debito3 = ff.createRecord("Detalhe-Body-Lancamento");
			debito3.setValue("ContaDebito", 0);
			debito3.setValue("ContaCredito", 313);
			debito3.setValue("Valor", new BigDecimal(1));
			debito3.setValue("CodigoDoHistorico", 1);
			debito3.setValue("ConteudoDoHistorico", "Lancamento de teste VARIOS DEBITOS PARA VARIOS CREDITOS: credito 1");
			debito3.setValue("CodigoDaLoja", FILIALMATRIZ);
			debito3.setValue("Sequencia", ++numeroSequencial);
			
			IRecord credito1 = ff.createRecord("Detalhe-Body-Lancamento");
			credito1.setValue("ContaDebito", 0);
			credito1.setValue("ContaCredito", 313);
			credito1.setValue("Valor", new BigDecimal(1));
			credito1.setValue("CodigoDoHistorico", 1);
			credito1.setValue("ConteudoDoHistorico", "Lancamento de teste VARIOS DEBITOS PARA VARIOS CREDITOS: credito 2");
			credito1.setValue("CodigoDaLoja", FILIALMATRIZ);
			credito1.setValue("Sequencia", ++numeroSequencial);
			
			IRecord credito2 = ff.createRecord("Detalhe-Body-Lancamento");
			credito2.setValue("ContaDebito", 0);
			credito2.setValue("ContaCredito", 313);
			credito2.setValue("Valor", new BigDecimal(1));
			credito2.setValue("CodigoDoHistorico", 1);
			credito2.setValue("ConteudoDoHistorico", "Lancamento de teste VARIOS DEBITOS PARA VARIOS CREDITOS: credito 3");
			credito2.setValue("CodigoDaLoja", FILIALMATRIZ);
			credito2.setValue("Sequencia", ++numeroSequencial);

			headerLancamento.addInnerRecord(debito1);
			headerLancamento.addInnerRecord(debito2);
			headerLancamento.addInnerRecord(debito3);
			headerLancamento.addInnerRecord(credito1);
			headerLancamento.addInnerRecord(credito2);
			
			return headerLancamento;
	}
	
	 static IRecord lancamentoUmDebitoParaVariosCreditos(IFlatFile<IRecord> ff){
		 	
		 	IRecord headerLancamento = ff.createRecord("Detalhe-Header-Lancamento");
			headerLancamento.setValue("TipoDeLancamento", 'D');
			headerLancamento.setValue("DataDoLancamento", new Date());
			headerLancamento.setValue("Usuario", USUARIO);
			headerLancamento.setValue("Sequencia", ++numeroSequencial);
			
			IRecord debito = ff.createRecord("Detalhe-Body-Lancamento");
			debito.setValue("ContaDebito", 5);
			debito.setValue("ContaCredito", 0);
			debito.setValue("Valor", new BigDecimal(9));
			debito.setValue("CodigoDoHistorico", 1);
			debito.setValue("ConteudoDoHistorico", "Lancamento de teste UM DEBITO PARA VARIOS CREDITOS: debito");
			debito.setValue("CodigoDaLoja", FILIALMATRIZ);
			debito.setValue("Sequencia", ++numeroSequencial);
			
			IRecord credito1 = ff.createRecord("Detalhe-Body-Lancamento");
			credito1.setValue("ContaDebito", 0);
			credito1.setValue("ContaCredito", 313);
			credito1.setValue("Valor", new BigDecimal(3));
			credito1.setValue("CodigoDoHistorico", 1);
			credito1.setValue("ConteudoDoHistorico", "Lancamento de teste UM DEBITO PARA VARIOS CREDITOS: credito 1");
			credito1.setValue("CodigoDaLoja", FILIALMATRIZ);
			credito1.setValue("Sequencia", ++numeroSequencial);
			
			IRecord credito2 = ff.createRecord("Detalhe-Body-Lancamento");
			credito2.setValue("ContaDebito", 0);
			credito2.setValue("ContaCredito", 313);
			credito2.setValue("Valor", new BigDecimal(3));
			credito2.setValue("CodigoDoHistorico", 1);
			credito2.setValue("ConteudoDoHistorico", "Lancamento de teste UM DEBITO PARA VARIOS CREDITOS: credito 2");
			credito2.setValue("CodigoDaLoja", FILIALMATRIZ);
			credito2.setValue("Sequencia", ++numeroSequencial);
			
			IRecord credito3 = ff.createRecord("Detalhe-Body-Lancamento");
			credito3.setValue("ContaDebito", 0);
			credito3.setValue("ContaCredito", 313);
			credito3.setValue("Valor", new BigDecimal(3));
			credito3.setValue("CodigoDoHistorico", 1);
			credito3.setValue("ConteudoDoHistorico", "Lancamento de teste UM DEBITO PARA VARIOS CREDITOS: credito 3");
			credito3.setValue("CodigoDaLoja", FILIALMATRIZ);
			credito3.setValue("Sequencia", ++numeroSequencial);

			headerLancamento.addInnerRecord(debito);
			headerLancamento.addInnerRecord(credito1);
			headerLancamento.addInnerRecord(credito2);
			headerLancamento.addInnerRecord(credito3);			
			
			return headerLancamento;
	}
	
	 static IRecord lancamentoUmCreditoParaVariosDebitos(IFlatFile<IRecord> ff){
		 	
		 IRecord headerLancamento = ff.createRecord("Detalhe-Header-Lancamento");
			headerLancamento.setValue("TipoDeLancamento", 'C');
			headerLancamento.setValue("DataDoLancamento", new Date());
			headerLancamento.setValue("Usuario", USUARIO);
			headerLancamento.setValue("Sequencia", ++numeroSequencial);
			
			IRecord credito = ff.createRecord("Detalhe-Body-Lancamento");
			credito.setValue("ContaDebito", 0);
			credito.setValue("ContaCredito", 313);
			credito.setValue("Valor", new BigDecimal(10));
			credito.setValue("CodigoDoHistorico", 1);
			credito.setValue("ConteudoDoHistorico", "Lancamento de teste UM CREDITO PARA VARIOS DEBITOS: credito");
			credito.setValue("CodigoDaLoja", FILIALMATRIZ);
			credito.setValue("Sequencia", ++numeroSequencial);
			
			IRecord debito1 = ff.createRecord("Detalhe-Body-Lancamento");
			debito1.setValue("ContaDebito", 5);
			debito1.setValue("ContaCredito", 0);
			debito1.setValue("Valor", new BigDecimal(2));
			debito1.setValue("CodigoDoHistorico", 1);
			debito1.setValue("ConteudoDoHistorico", "Lancamento de teste UM CREDITO PARA VARIOS DEBITOS: debito 1");
			debito1.setValue("CodigoDaLoja", FILIALMATRIZ);
			debito1.setValue("Sequencia", ++numeroSequencial);
			
			IRecord debito2 = ff.createRecord("Detalhe-Body-Lancamento");
			debito2.setValue("ContaDebito", 5);
			debito2.setValue("ContaCredito", 0);
			debito2.setValue("Valor", new BigDecimal(2));
			debito2.setValue("CodigoDoHistorico", 1);
			debito2.setValue("ConteudoDoHistorico", "Lancamento de teste UM CREDITO PARA VARIOS DEBITOS: debito 2");
			debito2.setValue("CodigoDaLoja", FILIALMATRIZ);
			debito2.setValue("Sequencia", ++numeroSequencial);
			
			IRecord debito3 = ff.createRecord("Detalhe-Body-Lancamento");
			debito3.setValue("ContaDebito", 5);
			debito3.setValue("ContaCredito", 0);
			debito3.setValue("Valor", new BigDecimal(6));
			debito3.setValue("CodigoDoHistorico", 1);
			debito3.setValue("ConteudoDoHistorico", "Lancamento de teste UM CREDITO PARA VARIOS DEBITOS: debito 3");
			debito3.setValue("CodigoDaLoja", FILIALMATRIZ);
			debito3.setValue("Sequencia", ++numeroSequencial);

			headerLancamento.addInnerRecord(credito);
			headerLancamento.addInnerRecord(debito1);
			headerLancamento.addInnerRecord(debito2);
			headerLancamento.addInnerRecord(debito3);			
			
			return headerLancamento;
	}
}
