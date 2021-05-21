package gestioneSegnalazioni;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.Connector;
import dominio.Segnalazione;
import dominio.TipoBacheca;

public class Inserimento implements Iinserimento {

	private Connector conn = Connector.getIstance();
	
	@Override
	public void aggiungiTag(Segnalazione segnalazione, String tag) throws SQLException {
		PreparedStatement st = conn.prepare("INSERT INTO Tag (nome, segnalazione) VALUES (?, ?)");
		st.setString(1, tag);
		//segnalazione.getId()
		st.setInt(2, 2342304);
		st.execute();
	}

	@Override
	public void inserisciInBacheca(Segnalazione segnalazione, TipoBacheca[] bacheca) {
		// TODO Auto-generated method stub
		
	}

}
