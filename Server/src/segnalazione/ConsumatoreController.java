package segnalazione;

import java.sql.SQLException;

import database.Connector;
import dominio.Profilo;
import dominio.Segnalazione;

public class ConsumatoreController implements IConsumatore {
	
	private Connector conn;
	
	public ConsumatoreController() throws SQLException {
		this.conn = Connector.getInstance();
	}
	
	public void prendiInCarico(Segnalazione segnalazione) {
	
	}

	public Segnalazione getMyJob(Profilo profilo) {
		return null;
	}

	public void richiediTerminazione(Segnalazione segnalazione) {
		
	}
}