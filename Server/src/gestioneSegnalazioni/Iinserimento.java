package gestioneSegnalazioni;

public interface Iinserimento {
	public void aggiungiTag(Segnalazione segnalazione,String tag);
	public void inserisciInBacheca(Segnalazione segnalazione,Bacheca[] bacheca);
}
