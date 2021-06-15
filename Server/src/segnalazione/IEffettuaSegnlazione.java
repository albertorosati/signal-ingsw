package segnalazione;

import java.sql.SQLException;

import dominio.Segnalazione;

public interface IEffettuaSegnlazione {
	public void effettuaSegnalazione(Segnalazione segnalazione) throws RuntimeException;

	public double[] getPosizione(String indirizzo) throws SQLException;
}
