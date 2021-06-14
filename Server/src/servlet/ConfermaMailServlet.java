package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import autenticazione.RegistrazioneController;
import database.Connector;
import dominio.Profilo;
import exceptions.EmailNotExistingException;
import json.JsonHandler;
import json.RespState;
import json.Response;

public class ConfermaMailServlet extends HttpServlet {

	private static final long serialVersionUID = 5903131929913779898L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (!req.getParameterMap().containsKey("body"))
			return;

		Response in = JsonHandler.getInstance().getGson().fromJson(req.getParameter("body"), Response.class);

		try {
			RegistrazioneController rc = new RegistrazioneController();
			Profilo utente = Profilo.getProfiloByEmail(Connector.getInstance(), in.getEmail());
			Response r = rc.convalidaEmail(utente, in.getHash());
			resp.getWriter().write(r.toJson());
		} catch (SQLException | EmailNotExistingException e) {
			resp.getWriter().write(new Response(RespState.FAILURE).toJson());
		}

		super.doPost(req, resp);
	}

}
