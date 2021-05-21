package gestioneSegnalazioni;

import dominio.Segnalazione;
import dominio.TipoBacheca;

public interface Iinserimento {
	public void aggiungiTag(Segnalazione segnalazione, String tag);

	public void inserisciInBacheca(Segnalazione segnalazione, TipoBacheca[] bacheca);
}
