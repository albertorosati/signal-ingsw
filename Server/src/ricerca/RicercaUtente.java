package ricerca;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List
import java.util.UUID;

import database.Connector;
import dominio.Profilo;
import dominio.RuoloUtente;

public class RicercaUtente implements IRicercaUtente {

	@Override
	public List<Profilo> cercaUtente(String name) throws SQLException {
		Connector conn = Connector.getIstance();
		if (conn == null)
			return null;
		PreparedStatement st = conn.prepare("SELECT * FROM Utenti WHERE email=?");
		st.setString(1, name);

		ResultSet rs = st.executeQuery();
		// Nessun risultato trovato
		if (!rs.first())
			return null;

		List<Profilo> result = new ArrayList<>();

		while (rs.next()) {
			//Bisogna inserire le carte ? e il comune ?
			result.add(new Profilo(UUID.fromString(rs.getString("id")),
					rs.getString("email"),
					rs.getString("nome"),
					rs.getString("cognome"),
					rs.getString("identificatore"),
					rs.getInt("sospeso") == 0 ? false : true,
					rs.getFloat("valutazione"),
					RuoloUtente.values()[rs.getInt("tipoUtente")],
					null,
					null));
		}
		
		return result;
	}

}
