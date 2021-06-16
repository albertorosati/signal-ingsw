package json;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import dominio.RuoloUtente;
import dominio.Segnalazione;

public class Response {
	
	private RespState state;
	private String nome;
	private String cognome;
	private RuoloUtente tipoUtente;
	private String hash;
	private String password;
	private String identificatore;
	private String email;
	private Boolean sospeso;
	private String comune;
		
	private String autore;
	private String titolo;
	private String descrizione;
	private String imageSrc;
	private Double lat;
	private Double lon;
	
	private String timestamp;
	
	private String[] tags;
	
	private String stemma;
	private Integer segnalazioniTotali;
	private Integer segnalazioniInCorso;
	
	private Integer segnalazione;
	
	//CERCA_SEGNALAZIONI
	//INPUT
	private String key;
	private boolean homePage;
	//OUTPUT
	private Signal[] risultatiRicerca;	//cambia Segnalazione.class --> Signal.class 
	private Signal[] bacheca;
	
	
	//PROFILO PERSONALE
	//INPUT
	//email
	//OUTPUT
	private Float reputazione;
	//segnalazioniTotali
	private Integer segnalazioniRisolte;
	private Card[] carte;
	
	//CHAT
	//INPUT
	private String messaggio;
	private Integer idSegnalazione;
	//OUTPUT
	private Integer idChat;
	private Mess[] messages;
	private Boolean esito;
	
	
	
	public boolean isHomePage() {
		return homePage;
	}

	public void setHomePage(boolean homePage) {
		this.homePage = homePage;
	}

	public Boolean getEsito() {
		return esito;
	}

	public void setEsito(Boolean esito) {
		this.esito = esito;
	}

	public void setSospeso(Boolean sospeso) {
		this.sospeso = sospeso;
	}

	public void setSegnalazioniTotali(Integer segnalazioniTotali) {
		this.segnalazioniTotali = segnalazioniTotali;
	}

	public void setSegnalazioniInCorso(Integer segnalazioniInCorso) {
		this.segnalazioniInCorso = segnalazioniInCorso;
	}

	public void setSegnalazione(Integer segnalazione) {
		this.segnalazione = segnalazione;
	}

	public void setBacheca(Signal[] bacheca) {
		this.bacheca = bacheca;
	}

	public void setSegnalazioniRisolte(Integer segnalazioniRisolte) {
		this.segnalazioniRisolte = segnalazioniRisolte;
	}

	public void setIdSegnalazione(Integer idSegnalazione) {
		this.idSegnalazione = idSegnalazione;
	}

	public void setIdChat(Integer idChat) {
		this.idChat = idChat;
	}

	public Signal[] getBacheca() {
		return bacheca;
	}

	public void setBacheca(Segnalazione[] bacheca) {
		List<Signal> res=new ArrayList<>();
		
		for(Segnalazione seg : bacheca)
			res.add(Signal.toSignal(seg));
		
		this.risultatiRicerca = res.toArray(new Signal[0]);
	}

	public void setRisultatiRicerca(Signal[] risultatiRicerca) {
		this.risultatiRicerca = risultatiRicerca;
	}

	public Signal[] getRisultatiRicerca() {
		return risultatiRicerca;
	}

	public void setRisultatiRicerca(Segnalazione[] risultatiRicerca) {
		List<Signal> res=new ArrayList<>();
		
		for(Segnalazione seg : risultatiRicerca)
			res.add(Signal.toSignal(seg));
		
		this.risultatiRicerca = res.toArray(new Signal[0]);
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getSegnalazioniRisolte() {
		return segnalazioniRisolte;
	}

	public void setSegnalazioniRisolte(int segnalazioniRisolte) {
		this.segnalazioniRisolte = segnalazioniRisolte;
	}

	public Card[] getCarte() {
		return carte;
	}

	public void setCarte(Card[] carte) {
		this.carte = carte;
	}

	public boolean isEsito() {
		return esito;
	}

	public void setEsito(boolean esito) {
		this.esito = esito;
	}

	public Mess[] getMessages() {
		return messages;
	}

	public void setMessages(Mess[] messages) {
		this.messages = messages;
	}

	public int getIdChat() {
		return idChat;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public int getIdSegnalazione() {
		return idSegnalazione;
	}
	
	public void setIdChat(int idChat) {
		this.idChat = idChat;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

	public void setIdSegnalazione(int idSegnalazione) {
		this.idSegnalazione = idSegnalazione;
	}

	public String getStemma() {
		return stemma;
	}
	
	public int getSegnalazione() {
		return segnalazione;
	}

	public void setSegnalazione(int s) {
		this.segnalazione = s;
	}
	
	public void setStemma(String stemma) {
		this.stemma = stemma;
	}

	public int getSegnalazioniTotali() {
		return segnalazioniTotali;
	}

	public void setSegnalazioniTotali(int segnalazioniTotali) {
		this.segnalazioniTotali = segnalazioniTotali;
	}

	public int getSegnalazioniInCorso() {
		return segnalazioniInCorso;
	}

	public void setSegnalazioniInCorso(int segnalazioniInCorso) {
		this.segnalazioniInCorso = segnalazioniInCorso;
	}

	public void setReputazione(Float reputazione) {
		this.reputazione = reputazione;
	}

	public Response(RespState state) {
		this.state = state;
	}
	
	public Response() {
		this.state = RespState.FAILURE;
	}
	
	public static void main(String[] args) {
		Response r = new Response(RespState.SUCCESS);
		r.setTipoUtente(RuoloUtente.AMMINISTRATORE);
		r.setTags(new String[] {"ciao", "ciao"});
		Gson gson = new Gson();
		System.out.println(gson.toJson(r));
	}
	
	public String toJson() {
		return JsonHandler.getInstance().getGson().toJson(this);
	}

	public RespState getState() {
		return state;
	}

	public void setState(RespState state) {
		this.state = state;
	}
	
	public Response setStateAndReturn(RespState state) {
		this.setState(state);
		return this;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}
	
	public boolean getSospeso() {
		return sospeso;
	}
	
	public void setSospeso(boolean sospeso) {
		this.sospeso=sospeso;
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

	public RuoloUtente getTipoUtente() {
		return tipoUtente;
	}

	public void setTipoUtente(RuoloUtente tipoUtente) {
		this.tipoUtente = tipoUtente;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIdentificatore() {
		return identificatore;
	}

	public void setIdentificatore(String identificatore) {
		this.identificatore = identificatore;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public float getReputazione() {
		return reputazione;
	}

	public void setReputazione(float reputazione) {
		this.reputazione = reputazione;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

}
