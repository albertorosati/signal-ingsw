package dominio;

public class Messaggio {

	private String testo;
	private Profilo from;

	public Messaggio(String testo, Profilo from) {
		this.testo = testo;
		this.from = from;
	}

}
