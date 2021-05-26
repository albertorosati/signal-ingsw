package ricerca;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.Connector;
import dominio.Profilo;
import exceptions.EmailNotExistingException;

public class RicercaUtenteController implements IRicercaUtente {

	@Override
	public List<Profilo> cercaUtente(String name) throws SQLException {
		Connector conn = Connector.getInstance();
		if (conn == null)
			return null;
		PreparedStatement st = conn.prepare("SELECT id FROM Utenti WHERE email=?");
		st.setString(1, name);

		ResultSet rs = st.executeQuery();
		List<Profilo> result = new ArrayList<>();

		// Nessun risultato trovato
		if (!rs.first())
			return result;

		while (rs.next()) {
			try {
				result.add(Profilo.getProfiloById(conn, Integer.parseInt(rs.getString("id"))));
			} catch (NumberFormatException | SQLException | EmailNotExistingException e) {
				// pressoché impossibile che venga lanciata questa eccezione
				// stiamo facendo la get di id di utenti di cui siamo sicuri
				// dell'esistenza
				e.printStackTrace();
			}
		}

		return result;
	}

}
