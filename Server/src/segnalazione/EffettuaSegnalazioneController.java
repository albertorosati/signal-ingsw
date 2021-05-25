package segnalazione;

import java.sql.PreparedStatement;
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
		PreparedStatement st = conn.prepare("INSERT INTO Segnalazioni (autore, titolo, descrizione, imageSrc, lat, lon) VALUES (?,?,?,?,?,?)");
		//conn.setInt(1, segnalazione.getAutore().getId());
		st.setString(2, segnalazione.getTitolo());
		st.setString(3, segnalazione.getDescrizione());
		st.setString(4, segnalazione.getMedia().get(0).getPosizione());
		st.setDouble(5, segnalazione.getPosizione().getLatitudine());
		st.setDouble(6, segnalazione.getPosizione().getLongitudine());
		st.execute();
	}

	@Override
	public double[] getPosizione(String indirizzo) throws SQLException {
		PreparedStatement st = conn.prepare("");
		return null;
	}

}
