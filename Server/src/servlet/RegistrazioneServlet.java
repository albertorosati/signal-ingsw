package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import autenticazione.RegistrazioneController;
import json.RespState;
import json.Response;

public class RegistrazioneServlet extends HttpServlet {

	private static final long serialVersionUID = -7667776164362400246L;

	/*
	 * email: “mario@rossi.it”, password: “password”, nome: “Mario”, cognome:
	 * “Rossi”, identificatore: “RSSMRAOoi1j09joidf”, comune: “Bologna”, tipoUtente:
	 * 0
	 * 
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (!req.getParameterMap().containsKey("email") || !req.getParameterMap().containsKey("password")
				|| !req.getParameterMap().containsKey("nome") || !req.getParameterMap().containsKey("cognome")
				|| !req.getParameterMap().containsKey("identificatore") || !req.getParameterMap().containsKey("comune")
				|| !req.getParameterMap().containsKey("tipoUtente"))
			return;

		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String nome = req.getParameter("nome");
		String cognome = req.getParameter("cognome");
		String identificatore = req.getParameter("identificatore");
		String comune = req.getParameter("comune");
		String tipoUtente = req.getParameter("tipoUtente");

		Response r = new Response(RespState.ERROR);
		
		try {
			RegistrazioneController rc = new RegistrazioneController();
			r = rc.registra(email, password, nome, cognome, identificatore, comune, tipoUtente);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		resp.getWriter().write(r.toJson());

		super.doPost(req, resp);
	}
}
