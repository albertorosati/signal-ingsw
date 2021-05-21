package segnalazione;

import database.Connector;
import dominio.Segnalazione;

public class EffettuaSegnalazione implements IEffettuaSegnlazione {

	private Connector conn = Connector.getInstance();
	
	@Override
	public void effettuaSegnalazione(Segnalazione segnalazione) {
		// TODO Auto-generated method stub

	}

	@Override
	public double[] getPosizione(String indirizzo) {
		// TODO Auto-generated method stub
		return null;
	}

}
