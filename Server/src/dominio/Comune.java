package dominio;

import java.util.List;

public class Comune {

	private String nome;
	private Multimedia stemma;
	private Multimedia foto;
	private List<Profilo> cittadini;

	public Comune(String nome, Multimedia stemma, Multimedia foto) {
		super();
		this.nome = nome;
		this.stemma = stemma;
		this.foto = foto;
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
