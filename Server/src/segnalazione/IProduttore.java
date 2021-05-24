package segnalazione;

import java.sql.SQLException;

import dominio.MetodoPagamento;
import dominio.Profilo;
import dominio.Segnalazione;

public interface IProduttore {
	public Segnalazione[] getMieSegnalazioni(Profilo utente);

	public void modificaTag(Segnalazione segnalazione, String old_tag, String new_tag);

	public void assegna(Segnalazione segnalazione, Profilo consumatore);

	public void rifiuta(Segnalazione segnalazione, Profilo richiedente);

	public void inserisciMetodoDiPagamento(Segnalazione segnalazione, MetodoPagamento pay);

	public void termina(Segnalazione segnalazione);

	public void inserisciValutazione(Profilo consumatore, int valutazione) throws SQLException;
}
