package dominio;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Segnalazione {

	private String titolo;
	private String descrizione;
	private LocalDateTime timestampCreazione;
	private LocalDateTime timestampAssegnazione;
	private Duration durataAssegnazione;
	private List<String> tags;
	private boolean visible;
	private Stato stato;
	private Comune comune;
	private Posizione posizione;
	private TipoBacheca tipoBacheca;
	private List<Profilo> richiedenti;
	private Profilo produttore;
	private Profilo consumatore;
	private Chat chat;
	private Assegnazione assegnazione; // TODO non mettere nel costruttore
	private List<Multimedia> medias;
	private boolean _public;

	public Segnalazione(String titolo, String descrizione, LocalDateTime timestampCreazione, List<String> tags,
			boolean visible, Stato stato, Posizione posizione, Profilo produttore, Profilo consumatore, Chat chat,
			List<Multimedia> medias, boolean _public) {
		this.titolo = titolo;
		this.descrizione = descrizione;
		this.timestampCreazione = timestampCreazione;
		this.tags = tags;
		this.visible = visible;
		this.stato = stato;
		this.posizione = posizione;
		this.produttore = produttore;
		this.consumatore = consumatore;
		this.chat = chat;
		this.medias = medias;
		this._public=_public;
	}

	public Stato impostaStato(Stato stato) {
		this.stato = stato;
		return this.stato;
	}

	public List<String> aggiungiTag(String tag) {
		this.tags.add(tag);
		return this.tags;
	}

	public void aggiungiRichiedente(Profilo richiedente) {
		this.richiedenti.add(richiedente);
	}

	public void assegna(Profilo profilo, Duration tempo) {
		this.consumatore = profilo;
		this.durataAssegnazione = tempo;
		this.timestampAssegnazione = LocalDateTime.now();
		this.assegnazione = new Assegnazione(this, this.produttore, this.consumatore, LocalDate.now().plus(tempo));
	}

	public void revocaAssegnazione() {
		this.assegnazione = null;
		this.consumatore = null;
	}

	public Chat avviaChat() {
		this.chat = new Chat(produttore, consumatore);
		return this.chat;
	}

	public Chat getChat() {
		return chat;
	}

	public String getTitolo() {
		return titolo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public LocalDateTime getTimestampCreazione() {
		return timestampCreazione;
	}

	public List<String> getTags() {
		return tags;
	}

	public Profilo getAutore() {
		return produttore;
	}

	public TipoBacheca getTipoBacheca() {
		return tipoBacheca;
	}

	public Stato getStato() {
		return stato;
	}

	public List<Multimedia> getMedia() {
		return medias;
	}

	public Posizione getPosizione() {
		return posizione;
	}
	
	public Comune getComune() {
		return comune;
	}

	public List<Profilo> getRichiedenti() {
		return richiedenti;
	}

	public Duration getDurataAssegnazione() {
		return durataAssegnazione;
	}

	public LocalDateTime getTimestampAssegnazione() {
		return timestampAssegnazione;
	}

	public boolean isVisible() {
		return visible;
	}

	public Profilo getProduttore() {
		return produttore;
	}

	public Profilo getConsumatore() {
		return consumatore;
	}

	public Assegnazione getAssegnazione() {
		return assegnazione;
	}

	public List<Multimedia> getMedias() {
		return medias;
	}
	
	public void setPublic(boolean val) {
		this._public=val;
	}

}
