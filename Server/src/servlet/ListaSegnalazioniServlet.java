package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dominio.Profilo;
import dominio.Segnalazione;
import exceptions.EmailNotExistingException;
import json.JsonHandler;
import json.RespState;
import json.Response;
import ricerca.RicercaController;
import segnalazione.ProduttoreController;
import userHomePage.UserHomePageController;

@WebServlet(value = "/getListaSegnalazioni")
public class ListaSegnalazioniServlet extends HttpServlet  {

	private static final long serialVersionUID = -7331320415566331879L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String body = Utils.getReqBody(req);
		
		if (body.isEmpty()) {
			resp.getWriter().write(new Response(RespState.FAILURE).toJson());
			return;
		}
		
		Response in = JsonHandler.getInstance().getGson().fromJson(body, Response.class);
		String email = in.getEmail();
		
		Response response=new Response();
		
		//Segnalazioni con ricerca
		if (in.getKey()!=null) {
			String key=in.getKey();
			
			try {
				RicercaController rc=new RicercaController();
				response.setRisultatiRicerca(rc.cercaSegnalazione(key).toArray(new Segnalazione[0]));									
			} catch (SQLException e) {
				//e.printStackTrace();
				response.setState(RespState.ERROR);
			}							
		}else if(in.isHomePage()) {
			//Get Bacheca
			try {
				UserHomePageController hp=new UserHomePageController();
				response.setBacheca(hp.getBacheca(email));							
				
			} catch (SQLException | EmailNotExistingException e) {
				//e.printStackTrace();
				response.setState(RespState.ERROR);
			} 
	
		}else {
			//Get MieSegnalazioni
			try {
				ProduttoreController pc=new ProduttoreController();
				response.setRisultatiRicerca(pc.getMieSegnalazioni(email));				
			} catch (SQLException | EmailNotExistingException e) {
				//e.printStackTrace();
				response.setState(RespState.ERROR);
			}
					
		}
		
		resp.getWriter().print(response.toJson());	
	}
	
}
