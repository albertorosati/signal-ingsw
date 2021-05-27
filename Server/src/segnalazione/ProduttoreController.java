package segnalazione;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.Connector;
import dominio.MetodoPagamento;
import dominio.Profilo;
import dominio.Segnalazione;

public class ProduttoreController implements IProduttore {

	private Connector conn;
	
	public ProduttoreController() throws SQLException {
		this.conn = Connector.getInstance();
	}
	
	@Override
	public Segnalazione[] getMieSegnalazioni(Profilo utente) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void modificaTag(Segnalazione segnalazione, String old_tag, String new_tag) {
		// TODO Auto-generated method stub

	}

	@Override
	public void assegna(Segnalazione segnalazione, Profilo consumatore) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rifiuta(Segnalazione segnalazione, Profilo richiedente) {
		// TODO Auto-generated method stub

	}

	@Override
	public void inserisciMetodoDiPagamento(Segnalazione segnalazione, MetodoPagamento pay) {
		// TODO Auto-generated method stub

	}

	@Override
	public void termina(Segnalazione segnalazione) {
		// TODO Auto-generated method stub

	}

	@Override
	public void inserisciValutazione(Profilo consumatore, int valutazione) throws SQLException {
		PreparedStatement st = conn.prepare("UPDATE Utenti SET reputazione = (reputazione + ?) / 2 WHERE email = ?");
		st.setDouble(1, (double) valutazione);
		st.setString(2, consumatore.getEmail());
		st.execute();
	}

}
