package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handshake.HandshakeController;
import json.JsonHandler;
import json.Response;

@WebServlet(value = "/inviamessaggio")
public class InviaMessaggioServlet extends HttpServlet  {

	private static final long serialVersionUID = 7431548028908059660L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if (!req.getParameterMap().containsKey("body")) {
			return;
		}
		
		HandshakeController hc=new HandshakeController(); 
		
		Response r = JsonHandler.getInstance().getGson().fromJson(req.getParameter("body"), Response.class);
		String email = r.getEmail();
		
		Response response=new Response();
		
		try {
			response=hc.sendMessage(r.getIdSegnalazione(), email, r.getMessaggio());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		resp.getWriter().print(response);		
		
		super.doPost(req, resp);
	}
	
}
