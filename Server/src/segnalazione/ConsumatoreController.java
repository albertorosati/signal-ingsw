package segnalazione;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import database.Connector;
import dominio.Profilo;
import dominio.Segnalazione;
import dominio.Stato;

public class ConsumatoreController implements IConsumatore {
	
	private Connector conn;
	
	public ConsumatoreController() throws SQLException {
		this.conn = Connector.getInstance();
	}
	
	public void prendiInCarico(Segnalazione segnalazione, Profilo p) {
		
		PreparedStatement st;
		ResultSet rs;
		boolean unique=true;
		
		try {
			st = conn.prepare("SELECT * FROM Assegnazione a WHERE a.consumatore = ? ;");
			st.setInt(1, p.getId());			
			rs=st.executeQuery();
			
			unique=rs.first();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//no segnalazioni gia' in corso
		if(unique) {
			segnalazione.aggiungiRichiedente(p);
		}		
	}

	public Segnalazione getMyJob(Profilo profilo) {
		Segnalazione seg=null;
		
		try {
			PreparedStatement st = conn.prepareReturn("SELECT * FROM Assegnazione a WHERE a.consumatore = ? ;");
			st.setString(1, profilo.getIdentificatore());
						
			ResultSet rs=st.executeQuery();
			seg=Segnalazione.getById(conn, rs.getInt("segnalazione"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return seg;
	}

	public void richiediTerminazione(Segnalazione segnalazione) {
		segnalazione.impostaStato(Stato.TERMINAZIONE);
		//aggiornamento tabella Assegnazione solo dopo aver dato valutazione
	}
}