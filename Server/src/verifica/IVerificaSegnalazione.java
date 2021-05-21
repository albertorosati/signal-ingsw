package verifica;

import dominio.Segnalazione;

public interface IVerificaSegnalazione {
	public void accetta(Segnalazione segnalazione);

	public void scarta(Segnalazione segnalazione);
}
