package gestioneSegnalazioni;

import java.sql.SQLException;

import dominio.Segnalazione;
import dominio.TipoBacheca;

public interface Iinserimento {
	public void aggiungiTag(Segnalazione segnalazione, String tag) throws SQLException;

	public void inserisciInBacheca(Segnalazione segnalazione, TipoBacheca[] bacheca);
}
