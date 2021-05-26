package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import autenticazione.RegistrazioneController;

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
		int tipoUtente;
		try {
			tipoUtente = Integer.parseInt(req.getParameter("tipoUtente"));
		} catch (NumberFormatException e) {
			return;
		}

		if (tipoUtente != 0 || tipoUtente != 1)
			return;

		if (email.isBlank() || password.isBlank() || nome.isBlank() || cognome.isBlank() || identificatore.isBlank()
				|| comune.isBlank())
			return;
		
		try {
			RegistrazioneController rc = new RegistrazioneController();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		super.doPost(req, resp);
	}
}
