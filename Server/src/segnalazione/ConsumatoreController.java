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
			st = conn.prepareReturn(
					"SELECT * FROM Assegnazione a JOIN Utenti u ON a.assegnatario = u.id "
					+ "WHERE u.identificatore = ? AND ;");
			st.setString(1, p.getIdentificatore());
			
			rs=st.executeQuery();
			
			unique=rs.first();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//no segnalazioni già in corso
		if(unique) {
			try {
				st = conn.prepareReturn(
						"INSERT INTO Assegnazione (inizio,segnalazione,consumatore)"
						+ "VALUES ();");
				st.setString(1, LocalDate.now().toString());
				st.setString(2, segnalazione.getTitolo());
				st.setString(3, p.getIdentificatore());
				
				st.execute();
				
				segnalazione.impostaStato(Stato.RICHIESTA_PRESA_IN_CARICO);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	public Segnalazione getMyJob(Profilo profilo) {
		Segnalazione seg=null;
		
		try {
			PreparedStatement st = conn.prepareReturn(
					"SELECT s.id"
					+ "FROM Segnalazioni s JOIN Utenti u ON s.assegnatario = u.id"
					+ "WHERE u.identificatore = ? ");
			st.setString(1, profilo.getIdentificatore());
						
			ResultSet rs=st.executeQuery();
			seg=Segnalazione.getById(conn, rs.getInt("id"));
			
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