package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Connector;
import dominio.Segnalazione;
import json.RespState;
import json.Response;

@WebServlet(value = "/getSegnalazione") 
public class SegnalazioneServlet extends HttpServlet {

	private static final long serialVersionUID = 7668719018548641975L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
		if (!req.getParameterMap().containsKey("segnalazione")) {
			return;
		}
		
		int id_segnalazione = Integer.parseInt(req.getParameter("segnalazione"));
		Response r = new Response(RespState.FAILURE);
		
		try {
			Segnalazione s = Segnalazione.getById(Connector.getInstance(), id_segnalazione);
			r.setState(RespState.SUCCESS);
			r.setAutore(s.getAutore().getNome() + "" + s.getAutore().getCognome());
			r.setTitolo(s.getTitolo());
			r.setDescrizione(s.getDescrizione());
			r.setImageSrc(s.getImage());
			r.setLat(s.getPosizione().getLatitudine());
			r.setLon(s.getPosizione().getLongitudine());
			r.setTimestamp(s.getTimestampCreazione().toString());
			r.setReputazione(s.getAutore().getValutazione());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		resp.getWriter().write(r.toJson());
	}
	
}
