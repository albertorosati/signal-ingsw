package ricerca;

import dominio.Profilo;
import dominio.Segnalazione;

public interface IRicerca {
	public Segnalazione[] getBacheca(Profilo utente);

	public Segnalazione[] cercaSegnalazione(String key);
}
