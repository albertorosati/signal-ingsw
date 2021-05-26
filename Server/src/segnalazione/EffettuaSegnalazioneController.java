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

	// Qui mi sa che bisogna cambiare la signature della funzione
	// deve prendere in ingresso i parametri per riempire una nuova
	// segnalazione
	@Override
	public void effettuaSegnalazione(Segnalazione segnalazione) throws SQLException {
		PreparedStatement st = conn.prepareReturn(
				"INSERT INTO Segnalazioni (autore, titolo, descrizione, imageSrc, lat, lon) VALUES (?,?,?,?,?,?)");
		// conn.setInt(1, segnalazione.getAutore().getId());
		st.setString(2, segnalazione.getTitolo());
		st.setString(3, segnalazione.getDescrizione());
		st.setDouble(5, segnalazione.getPosizione().getLatitudine());
		st.setDouble(6, segnalazione.getPosizione().getLongitudine());
		st.executeUpdate();
		ResultSet rs = st.getGeneratedKeys();
		if (rs.first()) {

		}
	}

	@Override
	public double[] getPosizione(String indirizzo) throws SQLException {
		PreparedStatement st = conn.prepare("");
		return null;
	}

}
