package dominio;

import java.util.List;

public class Profilo {

	private String email;
	private String nome;
	private String cognome;
	private String identificatore;
	private boolean sospeso;
	private float valutazione;
	private RuoloUtente ruolo;
	private Comune comuneResidenza;
	private List<CartaVirtuale> carteVirtuali;
	
	
	public Profilo(String email, String nome, String cognome, String identificatore, boolean sospeso, float valutazione,
			RuoloUtente ruolo, Comune comuneResidenza, List<CartaVirtuale> carteVirtuali) {
		super();
		this.email = email;
		this.nome = nome;
		this.cognome = cognome;
		this.identificatore = identificatore;
		this.sospeso = sospeso;
		this.valutazione = valutazione;
		this.ruolo = ruolo;
		this.comuneResidenza = comuneResidenza;
		this.carteVirtuali = carteVirtuali;
	}


	public String getEmail() {
		return email;
	}


	public String getNome() {
		return nome;
	}


	public String getCognome() {
		return cognome;
	}


	public String getIdentificatore() {
		return identificatore;
	}


	public boolean isSospeso() {
		return sospeso;
	}


	public float getValutazione() {
		return valutazione;
	}


	public RuoloUtente getRuolo() {
		return ruolo;
	}

	public void sospendi() {
		this.sospeso = true;
	}
	
	public float inserisciValutazione(int valutazione) { //nel dominio è un float ma per me essendo stelline ha senso metterla come int ¯\__(ツ)__/¯ 
		this.valutazione = (this.valutazione + valutazione)/(float) 2;
		return this.valutazione;
	}
	
}
