package gestioneSegnalazioni;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.Connector;
import dominio.Segnalazione;
import dominio.TipoBacheca;

public class InserimentoController implements Iinserimento {

	private Connector conn;
	
	public InserimentoController() throws SQLException {
		this.conn = Connector.getInstance();
	}
	
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
