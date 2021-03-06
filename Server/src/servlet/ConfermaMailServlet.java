package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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

@WebServlet(value = "/confermaRegistrazione")
public class ConfermaMailServlet extends HttpServlet {

	private static final long serialVersionUID = 5903131929913779898L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String body = Utils.getReqBody(req);
		
		if (body.isEmpty()) {
			resp.getWriter().write(new Response(RespState.FAILURE).toJson());
			return;
		}
		
		Response in = JsonHandler.getInstance().getGson().fromJson(body, Response.class);

		try {
			RegistrazioneController rc = new RegistrazioneController();
			Profilo utente = Profilo.getProfiloByEmail(Connector.getInstance(), in.getEmail());
			Response r = rc.convalidaEmail(utente, in.getHash());
			resp.getWriter().write(r.toJson());
		} catch (SQLException | EmailNotExistingException e) {
			resp.getWriter().write(new Response(RespState.FAILURE).toJson());
		}
		
	}

}
