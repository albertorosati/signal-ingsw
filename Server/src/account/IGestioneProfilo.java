package account;

import dominio.Profilo;

public interface IGestioneProfilo {
	public Profilo getInformazioni(String email);

	public void modificaInformazioni(Profilo utente, String old_value, String new_value);
}
