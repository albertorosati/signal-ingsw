package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import account.GestioneProfiloController;
import database.Connector;
import dominio.Profilo;
import exceptions.EmailNotExistingException;
import json.RespState;
import json.Response;

@WebServlet(value = "/getProfiloPersonale")
public class ProfiloPersonaleServlet extends HttpServlet {

	private static final long serialVersionUID = -1165521054940016044L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Response r = Utils.getResponseByReq(req);

		String email = r.getEmail();
		GestioneProfiloController gp = new GestioneProfiloController();
		
		Response res=new Response();

		try {
			res = gp.getInformazioni(email);
		} catch (SQLException e) {
			r.setState(RespState.FAILURE);
		}

		resp.getWriter().print(res.toJson());
	}

}
