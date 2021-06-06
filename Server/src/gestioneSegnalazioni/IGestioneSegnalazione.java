package gestioneSegnalazioni;

import dominio.MetodoPagamento;
import dominio.Profilo;
import dominio.Segnalazione;
import dominio.Stato;

public interface IGestioneSegnalazione {

	public void assegna(Segnalazione segnalazione, Profilo profilo);
	public void rifiuta(Segnalazione segnalazione, Profilo profilo);
	public void inserisciMetodoDiPagamento(Segnalazione segnalazione, MetodoPagamento metodo);
	public void cambioStato(Segnalazione segnalazione, Stato stato);
	public void revocaAssegnazione(Segnalazione segnalazione);
	
}
