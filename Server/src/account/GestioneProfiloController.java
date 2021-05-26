package account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.Connector;
import dominio.CartaVirtuale;
import dominio.Comune;
import dominio.Profilo;
import dominio.RuoloUtente;

public class GestioneProfiloController implements IGestioneProfilo {

	private Connector connector;

	public GestioneProfiloController() {
		try {
			this.connector = Connector.getInstance();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Profilo getInformazioni(String email) throws SQLException {
		Profilo res = null;
		PreparedStatement ps;
		ResultSet rs = null;

			ps = connector.prepare("SELECT * FROM Utenti WHERE email=?");
			ps.setString(1, email);
			rs = ps.executeQuery();
		

			// change tab Utente --> add comune
			String identificatore = rs.getString("identificatore");
			res = new Profilo(rs.getInt("id"), email, rs.getString("nome"), rs.getString("cognome"),
					identificatore, rs.getBoolean("sospeso"), rs.getFloat("reputazione"),
					RuoloUtente.values()[rs.getInt("tipoUtente")], null, getCarte(identificatore));
	

		return res;
	}

	private List<CartaVirtuale> getCarte(String idUtente) {
		List<CartaVirtuale> list = new ArrayList<>();

		PreparedStatement ps;
		ResultSet rs;

		try {
			ps = connector.prepare("SELECT * FROM Carte WHERE utente=?");
			ps.setInt(1, idUtente);
			rs = ps.executeQuery();

			while (rs.next()) {
				Comune c = new Comune(rs.getString("name"), rs.getString("stemma"), rs.getString("foto"));
				list.add(new CartaVirtuale(rs.getInt("id"), c));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public void modificaInformazioni(Profilo utente, String old_value, String new_value) {
		// sviluppi futuri
	}

}
