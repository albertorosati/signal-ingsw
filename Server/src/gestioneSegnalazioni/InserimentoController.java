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
		st.setInt(2, segnalazione.getId());
		st.execute();
	}

	@Override
	public void inserisciInBacheca(Segnalazione segnalazione, TipoBacheca[] bacheca) {
		
		//insert Segnalazione 
		Segnalazione.insert(conn, segnalazione);
		
		//insert comune
		
		//insert tags
		
		//insert cacheSegnalazioni 
		
	}

}
