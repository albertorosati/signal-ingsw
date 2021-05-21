package account;

import dominio.Profilo;

public interface IGestioneProfilo {
	public void getInformazioni(Profilo utente);

	public void modificaInformazioni(Profilo utente, String old_value, String new_value);
}
