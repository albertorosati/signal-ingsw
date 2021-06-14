package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListaSegnalazioniServlet extends HttpServlet  {

	private static final long serialVersionUID = -7331320415566331879L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//Segnalazioni con ricerca
		if (req.getParameterMap().containsKey("search")) {
			String search = req.getParameter("search");
		}
		//Mie-Segnalazioni
		if (req.getParameterMap().containsKey("id")) {
			int id = Integer.parseInt(req.getParameter("id"));
		}
		super.doPost(req, resp);
	}
	
}
