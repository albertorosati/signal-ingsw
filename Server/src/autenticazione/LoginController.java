package autenticazione;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.codec.digest.DigestUtils;

import database.Connector;
import dominio.RuoloUtente;
import json.RespState;
import json.Response;

public class LoginController implements ILogin {
	
	@Override
	public Response autentica(String username, String password) throws SQLException {
		Connector conn = Connector.getInstance();

		PreparedStatement st = conn.prepare("SELECT * FROM Utenti WHERE email=? AND password=?");
		String hash_pwd = DigestUtils.sha256Hex(password);

		Response resp = new Response();

		st.setString(1, username);
		st.setString(2, hash_pwd);
		ResultSet rs = st.executeQuery();

		// Nessun risultato trovato, una delle due:
		// 1- l'utente non esiste
		// 2- la password è errata
		if (!rs.first())
			return resp;
		else {

			// se l'utente è sospeso
			if (rs.getBoolean("sospeso"))
				return resp.setStateAndReturn(RespState.USER_SUSPENDED);

			// se l'utente NON è stato confermato
			if (!rs.getBoolean("confermato"))
				return resp.setStateAndReturn(RespState.USER_NOT_VERIFIED);

			// Bisogna inserire qui l'hash
			resp.setState(RespState.SUCCESS);
			resp.setEmail(rs.getString("email"));
			resp.setNome(rs.getString("nome"));
			resp.setCognome(rs.getString("cognome"));
			resp.setTipoUtente(RuoloUtente.values()[rs.getInt("tipoUtente")]);
			resp.setHash(DigestUtils.sha256Hex(System.currentTimeMillis() + "a"));
			return resp;
		}
	}

}
