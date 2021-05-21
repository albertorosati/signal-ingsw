package autenticazione;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Connector;

public class Login implements ILogin {

	@Override
	public String autentica(String username, String hash_passwd) {
		Connector conn = Connector.getIstance();

		if (conn == null)
			return "fail";

		try {
			PreparedStatement st = conn.prepare("SELECT * FROM Utenti WHERE email=? AND password=?");
			st.setString(1, username);
			st.setString(2, hash_passwd);
			ResultSet rs = st.executeQuery();
			if (rs.getFetchSize() == 0) {
				return "fail";
			} else if (rs.getFetchSize() == 1) {
				// Bisogna inserire qui l'hash
				return "hash?";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "fail";
	}

}
