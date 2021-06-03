package segnalazione;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Connector;
import dominio.Segnalazione;

public class EffettuaSegnalazioneController implements IEffettuaSegnlazione {

	private Connector conn;

	public EffettuaSegnalazioneController() throws SQLException {
		conn = Connector.getInstance();
	}

	@Override
	public void effettuaSegnalazione(Segnalazione segnalazione) throws SQLException {
		Segnalazione.insert(conn, segnalazione);
	}

	@Override
	public double[] getPosizione(String indirizzo) throws SQLException {
		//...
		return null;
	}

}
