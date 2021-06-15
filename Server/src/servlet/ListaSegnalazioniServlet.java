package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import json.JsonHandler;
import json.Response;
import ricerca.RicercaController;

@WebServlet(value = "/getListaSegnalazioni")
public class ListaSegnalazioniServlet extends HttpServlet  {

	private static final long serialVersionUID = -7331320415566331879L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Response r = JsonHandler.getInstance().getGson().fromJson(req.getParameter("body"), Response.class);
		String email = r.getEmail();
		
		Response response=new Response();
		
		//Segnalazioni con ricerca
		if (req.getParameterMap().containsKey("search")) {
			String search = req.getParameter("search");
			
			try {
				RicercaController rc=new RicercaController();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
		}
		//Mie-Segnalazioni
		if (req.getParameterMap().containsKey("id")) {
			int id = Integer.parseInt(req.getParameter("id"));
		}
		super.doPost(req, resp);
	}
	
}
