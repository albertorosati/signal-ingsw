package autenticazione;

import java.sql.SQLException;

import json.Response;

public interface ILogin {
	public Response autentica(String username, String password) throws SQLException;
}
