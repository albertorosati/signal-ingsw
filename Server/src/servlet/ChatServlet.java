package servlet;

import java.io.IOException;
import java.sql.SQLException;

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
		
		Response response=new Response();
				
		//if req = /api/inviamessaggio
		//        |-> nuvoMessaggio
		//elif req = /api/getChat
		//			|-> getOldMessages
		if(req.getRequestURI().startsWith("/api/getChat")) {

//			idSegnalazione: 23 
//			email: “pippo@pluto”
			String res;
			
			try {
				//already json
				res =hc.getMessages(r.getIdSegnalazione(), email);				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}else {
//			chat: 1, 
//			messaggio: “Lorem ipsum dolor sit amet”
			try {
				
				//make boolean output --> error DB Connection
				hc.sendMessage(r.getIdSegnalazione(), email, r.getMessaggio());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		resp.getWriter().print(response);		
		
		super.doPost(req, resp);
	}

}
