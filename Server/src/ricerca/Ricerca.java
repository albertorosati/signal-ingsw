package ricerca;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import database.Connector;
import dominio.Multimedia;
import dominio.Posizione;
import dominio.Profilo;
import dominio.Segnalazione;
import dominio.Stato;
import dominio.TipoFile;

public class Ricerca implements IRicerca {

	private Connector conn = Connector.getInstance();

	@Override
	public Segnalazione[] getBacheca(Profilo utente) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Segnalazione> cercaSegnalazione(String key) throws SQLException {
		PreparedStatement st = conn.prepare(
				"SELECT S.*,U.nome,U.cognome,\n" + 
				"GROUP_CONCAT(T.nome) tags\n" + 
				"FROM Segnalazioni S \n" + 
				"JOIN Utenti U ON S.autore=U.id\n" + 
				"JOIN Tag T ON T.segnalazione=S.id\n" + 
				"WHERE S.id IN (SELECT T.id FROM Tag T WHERE T.id = S.id AND T.nome LIKE '%?%') OR S.titolo LIKE '%?%' \n" + 
				"OR S.descrizione LIKE '%?%' \n" + 
				"OR S.indirizzo LIKE '%?%'");
			for (int i = 1; i <= 4; i++)
			st.setString(i, key);
		ResultSet rs = st.executeQuery();

		List<Segnalazione> result = new ArrayList<>();

		// la ricerca non ha prodotto risultati
		if (!rs.first())
			return result;

		while (rs.next()) {
			result.add(new Segnalazione(
					rs.getString("titolo"),
					rs.getString("descrizione"),
					LocalDateTime.parse(rs.getString("timestamp")),
					Arrays.asList(rs.getString("tags").split(",")),
					rs.getInt("visibile") == 0 ? false : true,
					Stato.values()[rs.getInt("stato")],
					new Posizione(rs.getDouble("lat"), rs.getDouble("lon")),
					null,
					null,
					null,
					List.of(new Multimedia(null, rs.getString("imgageSrc"), TipoFile.FOTO))
					));
		}
		
		return result;
	}

}
