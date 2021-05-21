package userHomePage;

import dominio.Profilo;
import dominio.Segnalazione;

public interface IUserHomePage {
	public Segnalazione[] getBacheca(Profilo profilo);
}
