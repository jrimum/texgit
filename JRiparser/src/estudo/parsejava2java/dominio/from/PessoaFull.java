package estudo.parsejava2java.dominio.from;

import estudo.parsejava2java.annotations.Converter;
import estudo.parsejava2java.annotations.RootToAdapt;
import estudo.parsejava2java.dominio.to.Pessoa;

@RootToAdapt(Pessoa.class)
public class PessoaFull {

	@Converter(attribute="nome")
	private String nome;
	
	@Converter(attribute="endereco.logradouro.nome")
	private String endereco;
	
	/**
	 * Exemplo de uso:
	 * 
	 * @Converter
	 * @Adapter(adpaterClass=MyAdapterRegex.class, params={"[0-9]{2}", "[0-9]{8}"}, attributesToAdapt={"telefone.ddd,", "telefone.numero"})
	 */
	private String telefone;
	
	@Converter(attribute="conjuge.nome")
	private String conjuge_nome;
	
	/**
	 * Exemplo de uso:
	 * 
	 * @Converter(adapter=@Adapter(...))
	 * OU
	 * @Converter(adapters={@Adapter(...), @Adapter(...), ...})
	 */
	private String conjuge_telefone;

	public PessoaFull(String nome, String endereco, String conjuge_nome) {
		super();
		this.nome = nome;
		this.endereco = endereco;
		this.conjuge_nome = conjuge_nome;
	}
	
	// getters e setters não são necessários no cliente devido ao uso do setAccessible(true) no AnnotationProcessor 
	

//	public String getNome() {
//		return nome;
//	}
//
//	public void setNome(String nome) {
//		this.nome = nome;
//	}
//
//	public String getEndereco() {
//		return endereco;
//	}
//
//	public void setEndereco(String endereco) {
//		this.endereco = endereco;
//	}
//
//	public String getTelefone() {
//		return telefone;
//	}
//
//	public void setTelefone(String telefone) {
//		this.telefone = telefone;
//	}
//
//	public String getConjuge_nome() {
//		return conjuge_nome;
//	}
//
//	public void setConjuge_nome(String conjuge_nome) {
//		this.conjuge_nome = conjuge_nome;
//	}
//
//	public String getConjuge_telefone() {
//		return conjuge_telefone;
//	}
//
//	public void setConjuge_telefone(String conjuge_telefone) {
//		this.conjuge_telefone = conjuge_telefone;
//	}
}
