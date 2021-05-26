package dominio;

public class Comune {

	private String nome;
	private Multimedia stemma;
	private Multimedia foto;

	public Comune(String nome, Multimedia stemma, Multimedia foto) {
		super();
		this.nome = nome;
		this.stemma = stemma;
		this.foto = foto;
	}
	
	public Comune(String nome, String stemma, String foto) {
		super();
		this.nome = nome;
		this.stemma = new Multimedia(stemma);
		this.foto = new Multimedia(foto);
	}
	

	public Multimedia getStemma() {
		return stemma;
	}

	public Multimedia getFoto() {
		return foto;
	}

	public String getNome() {
		return this.nome;
	}
}
