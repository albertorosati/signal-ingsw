package account;

import java.sql.SQLException;

import dominio.Profilo;
import json.Response;

public interface IGestioneProfilo {
	public Response getInformazioni(String email) throws SQLException;

	public void modificaInformazioni(Profilo utente, String old_value, String new_value);
	
	//richidiUpgrade()
}
