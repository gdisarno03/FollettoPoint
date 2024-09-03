package model;

public class UserBean {
	String nome;
	String cognome;
	String email;
	String pwd;
	String numero;
	String indirizzo;
	String metodo;
	int tipo;
	
	public UserBean() {
		nome = "";
		cognome = "";
		email = "";
		pwd = "";
		numero = "";
		indirizzo = "";
		tipo = 0;
		metodo="";
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "UserBean [nome=" + nome + ", cognome=" + cognome + ", email=" + email + ", pwd=" + pwd + ", numero="
				+ numero + ", indirizzo=" + indirizzo + ", tipo=" + tipo + "]";
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}
	
	
	
	
}
