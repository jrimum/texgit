package estudo.parsejava2java.dominio.to;

public class Endereco {

	private Logradouro logradouro;
	
	private int cep;
	
	private Endereco() {
		// TODO Auto-generated constructor stub
	}
	
	public Logradouro getLogradouro() {
		return logradouro;
	}

	public int getCep() {
		return cep;
	}
	
	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}
	
	

}
