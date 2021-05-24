package dominio;

import java.util.List;
import java.util.UUID;

public class Profilo {

	private UUID id;
	private String email;
	private String nome;
	private String cognome;
	private String identificatore;
	private boolean sospeso;
	private float valutazione;
	private RuoloUtente ruolo;
	private Comune comuneResidenza;
	private List<CartaVirtuale> carteVirtuali;

	public Profilo(UUID id, String email, String nome, String cognome, String identificatore, boolean sospeso,
			float valutazione, RuoloUtente ruolo, Comune comuneResidenza, List<CartaVirtuale> carteVirtuali) {
		super();
		this.id = id;
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

	public UUID getId() {
		return id;
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

	public float inserisciValutazione(int valutazione) {
		// nel dominio è un float ma per me essendo stelline ha senso
		// metterla come int ¯\__(ツ)__/¯
		this.valutazione = (this.valutazione + valutazione) / (float) 2;
		return this.valutazione;
	}

	public Comune getComuneResidenza() {
		return comuneResidenza;
	}

	public List<CartaVirtuale> getCarteVirtuali() {
		return carteVirtuali;
	}
	
	public int getTotalPoints() {
		int res=0;
		for(CartaVirtuale card : this.carteVirtuali)
			res+=card.getSaldo();
		
		return res;
	}
	
	public boolean addPoint(Comune comune,int points) {
		boolean res=false;
		
		for(CartaVirtuale card : this.carteVirtuali)
			if(card.getComune().equals(comune)) {
				card.aggiungiPunti(points);
				res=true;
			}
		
		return res;
	}

}
