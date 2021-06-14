package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Connector;
import dominio.Posizione;
import dominio.Profilo;
import dominio.Segnalazione;
import exceptions.EmailNotExistingException;
import json.JsonHandler;
import json.RespState;
import json.Response;
import segnalazione.EffettuaSegnalazioneController;

public class NuovaSegnalazioneServlet extends HttpServlet {

	private static final long serialVersionUID = 6132644101777643724L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Response res = new Response();

		if (!req.getParameterMap().containsKey("body")) {
			res.setState(RespState.FAILURE);
		}

		Response in = JsonHandler.getInstance().getGson().fromJson(req.getParameter("body"), Response.class);

		String email = in.getEmail();
		String titolo = in.getTitolo();
		String descrizione = in.getDescrizione();
		String[] allTags = in.getTags();
		String img = in.getImageSrc();
		double lat = in.getLat();
		double lon = in.getLon();
		String comune = in.getComune(); // TODO: aggiungere nel json della request

		List<String> tags = new ArrayList<>();
		for (String s : allTags) {
			tags.add(s.trim());
		}

		Segnalazione segnalazione;
		try {
			EffettuaSegnalazioneController controller = new EffettuaSegnalazioneController();
			Connector conn = Connector.getInstance();
			segnalazione = new Segnalazione(titolo, descrizione, tags, new Posizione(lat, lon), comune,
					Profilo.getProfiloByEmail(conn, email), img);
			if (res.getState() != RespState.FAILURE) { // ext: controllare che l'utente non ne abbia fatta un'altra
														// negli ultimi 5 minuti
				controller.effettuaSegnalazione(segnalazione);
				res.setState(RespState.SUCCESS);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmailNotExistingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		resp.getWriter().print(res);
		super.doPost(req, resp);
		// cambiamento fittizio
	}

}
