package account;

import java.sql.SQLException;

import dominio.Profilo;

public interface IGestioneProfilo {
	public Profilo getInformazioni(String email) throws SQLException;

	public void modificaInformazioni(Profilo utente, String old_value, String new_value);
	
	//richidiUpgrade()
}
