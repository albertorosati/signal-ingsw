package gestioneSegnalazioni;

import java.sql.SQLException;

import database.Connector;
import dominio.MetodoPagamento;
import dominio.Profilo;
import dominio.Segnalazione;
import dominio.Stato;

public class GestioneSegnalazione implements IGestioneSegnalazione {

	private Connector conn;
	
	public GestioneSegnalazione() throws SQLException {
		this.conn=Connector.getInstance();
	}

	@Override
	public void assegna(Segnalazione segnalazione, Profilo profilo) {
		segnalazione.assegna(profilo);
	}

	@Override
	public void rifiuta(Segnalazione segnalazione, Profilo profilo) {
		segnalazione.rifiuta(profilo);
	}

	@Override
	public void inserisciMetodoDiPagamento(Segnalazione segnalazione, MetodoPagamento metodo) {
		segnalazione.setPay(metodo);
	}

	@Override
	public void cambioStato(Segnalazione segnalazione, Stato stato) {
		segnalazione.impostaStato(stato);
	}

	@Override
	public void revocaAssegnazione(Segnalazione segnalazione) {
		segnalazione.revocaAssegnazione();		
	}

	

}
