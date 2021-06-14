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
		
		HandshakeController hc=new HandshakeController(); 
		
		Response r = JsonHandler.getInstance().getGson().fromJson(req.getParameter("body"), Response.class);
		String email = r.getEmail();
		
				
		//if req = /api/inviamessaggio
		//        |-> nuvoMessaggio
		//elif req = /api/getChat
		//			|-> getOldMessages
		if(req.getRequestURI().startsWith("/api/getChat")) {

//			idSegnalazione: 23 
//			email: “pippo@pluto”
			
			hc.sendMessage(r.getIdSegnalazione(), email, r.getMessaggio());
		}else {
//			chat: 1, 
//			messaggio: “Lorem ipsum dolor sit amet”
		}
		
		
		
		
		super.doPost(req, resp);
	}

}
