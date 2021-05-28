package segnalazione;

import dominio.Profilo;
import dominio.Segnalazione;

public interface IConsumatore {
	public void prendiInCarico(Segnalazione segnalazione, Profilo profilo);

	public Segnalazione getMyJob(Profilo profilo);

	public void richiediTerminazione(Segnalazione segnalazione);
}
