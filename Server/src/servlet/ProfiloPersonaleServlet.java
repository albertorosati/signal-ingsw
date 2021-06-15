package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import account.GestioneProfiloController;
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
		GestioneProfiloController gp=new GestioneProfiloController();
		
		Response r = new Response();	
		
		try {
			r=gp.getInformazioni(email);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		resp.getWriter().print(r);
		
		super.doPost(req, resp);
	}
	
}
