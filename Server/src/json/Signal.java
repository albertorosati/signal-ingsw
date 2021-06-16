 package json;


import java.util.ArrayList;
import java.util.List;
import dominio.Profilo;
import dominio.Segnalazione;

public class Signal {

	private String titolo;
	private String descrizione;
	private List<String> tags;
	private int stato;
	//private String comune;
	private double lat;
	private double lon;
	//private List<Profile> richiedenti;
	private Profile produttore;
	private Profile consumatore;
	private int idChat;
	private String imageSrc;
	private String timestamp;
	
	public Signal() {
		super();
	}
	
	public static Signal toSignal(Segnalazione seg) {
		Signal res=new Signal();
		
		if(seg.getTitolo()!=null)
			res.setTitolo(seg.getTitolo());
		else
			res.setTitolo("");
		
			
		res.setDescrizione(seg.getDescrizione());
		
		
		if(seg.getTags()!=null)
			res.setTags(seg.getTags());
		else res.setTags(null);
		
		res.setStato(seg.getStato().ordinal());
		
		/*if(seg.getComune().getNome()!=null)
			res.setComune(seg.getComune().getNome());
		else res.setComune(""); */
		
		res.setLat(seg.getPosizione().getLatitudine());
		
		res.setLon(seg.getPosizione().getLongitudine());
		
		res.setIdChat(seg.getId());
		
		if(seg.getImage()!=null)
			res.setImageSrc(seg.getImage());
		else res.setImageSrc("");
		
		if(res.getProduttore()!=null)
			res.setProduttore(Profile.toProfile(seg.getConsumatore()));
		else res.setProduttore(null);
		
		
		if(res.getConsumatore()!=null)
			res.setConsumatore(Profile.toProfile(seg.getConsumatore()));
		else res.setConsumatore(null);
		/*
		if(res.getRichiedenti()!=null)
			res.setRichiedenti(seg.getRichiedenti());
		else res.setRichiedenti(null);
		*/
		return res;
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

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public int getStato() {
		return stato;
	}

	public void setStato(int stato) {
		this.stato = stato;
	}

	/*public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}*/

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
/*
	public List<Profile> getRichiedenti() {
		return richiedenti;
	}

	public void setRichiedenti(List<Profilo> richiedenti) {
		List<Profile> res=new ArrayList<>();
		
		for(Profilo pr : richiedenti)
			res.add(Profile.toProfile(pr));
		
		this.richiedenti = res;
	}
*/
	public Profile getProduttore() {
		return produttore;
	}

	public void setProduttore(Profile produttore) {
		this.produttore = produttore;
	}

	public Profile getConsumatore() {
		return consumatore;
	}

	public void setConsumatore(Profile consumatore) {
		this.consumatore = consumatore;
	}

	public int getIdChat() {
		return idChat;
	}

	public void setIdChat(int idChat) {
		this.idChat = idChat;
	}

	public String getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
}
