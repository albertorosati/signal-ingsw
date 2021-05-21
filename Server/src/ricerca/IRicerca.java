package ricerca;

public interface IRicerca {
	public Segnalazione[] getBacheca(Profilo utente);
	public Segnalazione[] cercaSegnalazione(String key); 
}
