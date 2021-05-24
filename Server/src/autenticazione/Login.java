package autenticazione;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Connector;

public class Login implements ILogin {

	@Override
	public String autentica(String username, String hash_passwd) {
		Connector conn = Connector.getInstance();

		if (conn == null)
			return "fail";

		try {
			PreparedStatement st = conn.prepare("SELECT * FROM Utenti WHERE email=? AND password=?");
			st.setString(1, username);
			st.setString(2, hash_passwd);
			ResultSet rs = st.executeQuery();
			// Nessun risultato trovato, una delle due:
			// 1- l'utente non esiste
			// 2- la password è errata
			if (!rs.first())
				return "fail";
			else {
				// Bisogna inserire qui l'hash
				return "hash?";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "fail";
	}

}
