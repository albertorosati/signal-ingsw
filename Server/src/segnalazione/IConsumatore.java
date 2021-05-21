package segnalazione;

public interface IConsumatore {
	public void prendiInCarico(Segnalazione segnalazione);
	public Segnalazione getMyJob(Profilo profilo);
	public void richiediTerminazione(Segnalazione segnalazione);
}
