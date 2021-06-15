package segnalazione;

import java.sql.SQLException;

import dominio.MetodoPagamento;
import dominio.Profilo;
import dominio.Segnalazione;
import exceptions.EmailNotExistingException;

public interface IProduttore {
	public void modificaTag(Segnalazione segnalazione, String old_tag, String new_tag);

	public void assegna(Segnalazione segnalazione, Profilo consumatore);

	public void rifiuta(Segnalazione segnalazione, Profilo richiedente);

	public void inserisciMetodoDiPagamento(Segnalazione segnalazione, MetodoPagamento pay);

	public void termina(Segnalazione segnalazione);

	public void inserisciValutazione(Profilo consumatore, int valutazione) throws SQLException;

	public Segnalazione[] getMieSegnalazioni(String email) throws SQLException, EmailNotExistingException;
}
