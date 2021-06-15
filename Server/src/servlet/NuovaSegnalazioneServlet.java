package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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

@WebServlet(value = "/nuovaSegnalazione")
public class NuovaSegnalazioneServlet extends HttpServlet {

	private static final long serialVersionUID = 6132644101777643724L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Response res = new Response();
		String body = Utils.getReqBody(req);
		
		if (body.isEmpty()) {
			resp.getWriter().write(new Response(RespState.FAILURE).toJson());
			return;
		}
		
		Response in = JsonHandler.getInstance().getGson().fromJson(body, Response.class);

		String email = in.getEmail();
		String titolo = in.getTitolo();
		String descrizione = in.getDescrizione();
		String[] allTags = in.getTags();
		String img = in.getImageSrc();
		double lat = in.getLat();
		double lon = in.getLon();
		String comune = in.getComune(); 
		
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
			
			controller.effettuaSegnalazione(segnalazione);
			res.setState(RespState.SUCCESS);
			
		} catch (SQLException | EmailNotExistingException e) {
			//e.printStackTrace();
			res.setState(RespState.ERROR);
		} catch (RuntimeException e) {
			//e.printStackTrace();
			//utente ha gia' fatto segnalazione negli ultimi 5 minuti
			res.setState(RespState.FAILURE);
		}

		resp.getWriter().print(res.toJson());
		
		// cambiamento fittizio
	}

}
