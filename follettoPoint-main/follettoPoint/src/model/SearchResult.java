package model;

public class SearchResult {
	private String nome;
	private int id;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "SearchResult [nome=" + nome + ", id=" + id + "]";
	}
	
	
}
