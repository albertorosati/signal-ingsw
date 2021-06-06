package json;

import com.google.gson.Gson;

import dominio.RuoloUtente;

public class Response {
	
	private RespState state;
	private String nome;
	private String cognome;
	private RuoloUtente tipoUtente;
	private String hash;
	private String password;
	private String identificatore;
	private String email;
	private boolean sospeso;
	private String comune;
		
	private String autore;
	private String titolo;
	private String descrizione;
	private String imageSrc;
	private Double lat;
	private Double lon;
	
	private String timestamp;
	private Float reputazione;
	private String[] tags;
	
	private String stemma;
	private int segnalazioniTotali;
	private int segnalazioniInCorso;
	
	public String getStemma() {
		return stemma;
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
