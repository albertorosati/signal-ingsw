package ricerca;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.Connector;
import dominio.Profilo;
import dominio.Segnalazione;

public class RicercaController implements IRicerca {

	private Connector conn;

	public RicercaController() throws SQLException {
		this.conn = Connector.getInstance();
	}

	@Override
	public List<Segnalazione> cercaSegnalazione(String key) throws SQLException {
		PreparedStatement st = conn.prepare("SELECT S.*" + "FROM Segnalazioni S \n"
				+ "JOIN Tag T ON T.segnalazione=S.id\n"
				+ "WHERE S.id IN (SELECT T.id FROM Tag T WHERE T.id = S.id AND T.nome LIKE '%?%') OR S.titolo LIKE '%?%' \n"
				+ "OR S.descrizione LIKE '%?%' \n" + "OR S.indirizzo LIKE '%?%'");
		for (int i = 1; i <= 4; i++)
			st.setString(i, key);
		ResultSet rs = st.executeQuery();

		List<Segnalazione> result = new ArrayList<>();

		// la ricerca non ha prodotto risultati
		if (!rs.first())
			return result;

		while (rs.next()) {
			result.add(Segnalazione.getById(conn, rs.getInt("id")));
		}

		return result;
	}

}
