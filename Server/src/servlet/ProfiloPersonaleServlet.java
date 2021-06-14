package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Connector;
import dominio.Profilo;
import exceptions.EmailNotExistingException;
import json.Response;

public class ProfiloPersonaleServlet extends HttpServlet {

	private static final long serialVersionUID = -1165521054940016044L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (!req.getParameterMap().containsKey("email")) {
			return;
		}
		
		String email = req.getParameter("email");
		Random rand = new Random();
		
		try {
			Profilo p = Profilo.getProfiloByEmail(Connector.getInstance(), email);
			Response r = new Response();
			r.setReputazione(p.getValutazione());
			r.setSegnalazioniTotali(rand.nextInt(10)+5);
			r.setSegnalazioniInCorso(rand.nextInt(5)+4);
			
		} catch (SQLException | EmailNotExistingException e) {
			e.printStackTrace();
		}
		
		
		super.doPost(req, resp);
	}
	
}
