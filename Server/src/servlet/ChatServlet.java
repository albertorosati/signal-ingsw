package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dominio.Profilo;
import handshake.HandshakeController;
import json.JsonHandler;
import json.Response;

public class ChatServlet extends HttpServlet {

	private static final long serialVersionUID = -7247857151701399985L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (!req.getParameterMap().containsKey("body")) {
			return;
		}
		
		Response r = JsonHandler.getInstance().getGson().fromJson(req.getParameter("body"), Response.class);
		String email = r.getEmail();
				
		//getMessageText
		
		HandshakeController hc=new HandshakeController(); 
		
		//
		
		
		super.doPost(req, resp);
	}

}
