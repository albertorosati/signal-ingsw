package servlet;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import json.RespState;
import json.Response;
import segnalazione.EffettuaSegnalazioneController;

public class NuovaSegnalazioneServlet extends HttpServlet  {

	private static final long serialVersionUID = 6132644101777643724L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Response res = new Response();
		
		if(!req.getParameterMap().containsKey("email") || !req.getParameterMap().containsKey("titolo") 
			|| !req.getParameterMap().containsKey("descrizione")  || !req.getParameterMap().containsKey("tags")
			|| !req.getParameterMap().containsKey("imgSource")|| !req.getParameterMap().containsKey("lat")
			|| !req.getParameterMap().containsKey("lon")
				) {
			res.setState(RespState.FAILURE);
		}
		
		String email = req.getParameter("email");
		String titolo = req.getParameter("titolo");
		String descrizione = req.getParameter("descrizione");
		String allTags = req.getParameter("tags");
		String img = req.getParameter("imgSource");
		double lat = Double.parseDouble(req.getParameter("lat"));
		double lon = Double.parseDouble(req.getParameter("lon"));
		String comune = req.getParameter("comune"); //TODO: aggiungere nel json della request
		
		String[] tagsString = allTags.split(",");
		List<String> tags = new ArrayList<>();
		for(String s : tagsString) {
			tags.add(s.trim());
		}
		
		Segnalazione segnalazione;
		EffettuaSegnalazioneController controller = null;
		try {
			Connector conn = Connector.getInstance();
			segnalazione = new Segnalazione(titolo, descrizione, tags, new Posizione(lat, lon), comune, Profilo.getProfiloByEmail(conn, email),img);
			if(res.getState() != RespState.FAILURE) { //ext: controllare che l'utente non ne abbia fatta un'altra negli ultimi 5 minuti
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
		//cambiamento fittizio
	}
	
}
