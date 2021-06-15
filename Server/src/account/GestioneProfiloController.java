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
import exceptions.EmailNotExistingException;
import json.Card;
import json.RespState;
import json.Response;

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
	public Response getInformazioni(String email) throws SQLException {
		Response resp = new Response();

		PreparedStatement ps = connector.prepare("SELECT * FROM Utenti WHERE email=?");
		ps.setString(1, email);
		ResultSet rs = ps.executeQuery();

		try {
			Profilo res = Profilo.getProfiloByEmail(connector, email);

			resp = res.toResponse();

			// getSegnalazioniTotali (effettuate)
			resp.setSegnalazioniTotali(res.getTotalSegnalazioniEffettute());
			// getSegnalazioniRisolte
			resp.setSegnalazioniRisolte(res.getTotalSegnalazioniRisolte());

			// list Carte: CarteVirtuali.class --> Card.class
			if (res.getCarteVirtuali() != null) {
				List<Card> carte = new ArrayList<>();
				for (CartaVirtuale c : res.getCarteVirtuali())
					carte.add(Card.toCard(c));
				
				resp.setCarte((Card[]) carte.toArray());
			}


			resp.setState(RespState.SUCCESS);

		} catch (SQLException | EmailNotExistingException e) {
			// e.printStackTrace();
			resp.setState(RespState.USER_NOT_EXISTING);
		}

		return resp;
	}

	@Override
	public void modificaInformazioni(Profilo utente, String old_value, String new_value) {
		// sviluppi futuri
	}

}
