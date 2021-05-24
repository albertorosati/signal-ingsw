package verifica;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import database.Connector;

public class VerificaController implements IVerificaSegnalazione {
	
	private Connector conn;
	
	public VerificaController() throws SQLException {
		this.conn = Connector.getInstance();
	}
	
	public void accetta(Segnalazione segnalazione) {
		Profilo consumer=segnalazione.getConsumatore();
		segnalazione.impostaStato(Stato.APPROVATA);
		
		//Comune comune=traslate(segnalazione.Posizione)-->Comune
		consumer.addPoint(comune,50);
	}

	public void scarta(Segnalazione segnalazione) {
		//...
	}
}