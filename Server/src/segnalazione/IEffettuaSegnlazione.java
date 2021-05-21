package segnalazione;

import dominio.Segnalazione;

public interface IEffettuaSegnlazione {
	public void effettuaSegnalazione(Segnalazione segnalazione);

	public double[] getPosizione(String indirizzo);
}
