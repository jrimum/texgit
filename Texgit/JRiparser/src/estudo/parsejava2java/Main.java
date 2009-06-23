package estudo.parsejava2java;

import estudo.parsejava2java.dominio.from.PessoaFull;
import estudo.parsejava2java.dominio.to.Pessoa;
import estudo.parsejava2java.engine.AnnotationProcessor;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		PessoaFull pessoaFull = new PessoaFull("João", "Rua dos encantos", "Maria");
		// não são necessário getters e setters
//		pessoaFull.setNome("Jo�o");
//		pessoaFull.setEndereco("Rua dos encantos");
//		pessoaFull.setConjuge_nome("Maria");
		
		Pessoa pessoa = (Pessoa) new AnnotationProcessor(pessoaFull).process();
		
		System.out.println("Nome da Pessoa: " + pessoa.getNome());
		System.out.println("Rua da Pessoa: " + pessoa.getEndereco().getLogradouro().getNome());
		System.out.println("Nome do Cônjuge: " + pessoa.getConjuge().getNome());
	}

}
