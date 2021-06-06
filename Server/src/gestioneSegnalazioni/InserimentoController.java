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
		segnalazione.aggiungiTag(tag);
	}

	//in realta le bacheche non servono. Basta vedere se la segnalazione
	//è pubblica o meno
	@Override
	public void inserisciInBacheca(Segnalazione segnalazione, TipoBacheca[] bacheca) {
		
		//insert Segnalazione 
		//Segnalazione.insert(conn, segnalazione);
		segnalazione.inserisciInBacheca();		
		
	}

}
