package segnalazione;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import database.Connector;

public class ConsumatoreController implements IConsumatore {
	
	private Connector conn;
	
	public ConsumatoreController() throws SQLException {
		this.conn = Connector.getInstance();
	}
	
	public void prendiInCarico(Segnalazione segnalazione) {
	
	}

	public Segnalazione getMyJob(Profilo profilo) {
		
	}

	public void richiediTerminazione(Segnalazione segnalazione) {
		
	}
}