package verifica;

import java.sql.SQLException;

import database.Connector;
import dominio.Profilo;
import dominio.Segnalazione;
import dominio.Stato;


public class VerificaController implements IVerificaSegnalazione {
	
	private Connector conn;
	
	public VerificaController() throws SQLException {
		this.conn = Connector.getInstance();
	}
	
	public void accetta(Segnalazione segnalazione) {
		Profilo consumer=segnalazione.getConsumatore();
		segnalazione.impostaStato(Stato.APPROVATA);
		
		consumer.addPoint(segnalazione.getComune(),50);
	}

	public void scarta(Segnalazione segnalazione) {
		segnalazione.impostaStato(Stato.RIFIUTATA);
	}
}