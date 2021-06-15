package segnalazione;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.Connector;
import dominio.MetodoPagamento;
import dominio.Profilo;
import dominio.Segnalazione;
import dominio.Stato;
import exceptions.EmailNotExistingException;

public class ProduttoreController implements IProduttore {

	private Connector conn;
	
	public ProduttoreController() throws SQLException {
		this.conn = Connector.getInstance();
	}
	
	@Override
	public Segnalazione[] getMieSegnalazioni(String email) throws SQLException, EmailNotExistingException {
		Profilo utente=Profilo.getProfiloByEmail(conn, email);
		return utente.getMySegnalazioni();
	}

	@Override
	public void modificaTag(Segnalazione segnalazione, String old_tag, String new_tag) {
		if(segnalazione.getStato().compareTo(Stato.IN_APPROVAZIONE)==0)
			segnalazione.modificaTag(old_tag, new_tag);
	}

	@Override
	public void assegna(Segnalazione segnalazione, Profilo consumatore) {
		segnalazione.assegna(consumatore);
	}

	@Override
	public void rifiuta(Segnalazione segnalazione, Profilo richiedente) {
		segnalazione.rifiuta(richiedente);
	}

	@Override
	public void inserisciMetodoDiPagamento(Segnalazione segnalazione, MetodoPagamento pay) {
		segnalazione.setPay(pay);
	}

	@Override
	public void termina(Segnalazione segnalazione) {
		segnalazione.termina();
	}

	@Override
	public void inserisciValutazione(Profilo consumatore, int valutazione) throws SQLException {
		consumatore.inserisciValutazione(valutazione);
	}

	
}
