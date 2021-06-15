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
import json.RespState;
import json.Response;

@WebServlet(value = "/inviamessaggio")
public class InviaMessaggioServlet extends HttpServlet  {

	private static final long serialVersionUID = 7431548028908059660L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String body = Utils.getReqBody(req);
		
		if (body.isEmpty()) {
			resp.getWriter().write(new Response(RespState.FAILURE).toJson());
			return;
		}
		
		Response r = JsonHandler.getInstance().getGson().fromJson(body, Response.class);
		HandshakeController hc=new HandshakeController(); 
		String email = r.getEmail();
		
		Response response=new Response();
		
		try {
			response=hc.sendMessage(r.getIdSegnalazione(), email, r.getMessaggio());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		
		resp.getWriter().print(response.toJson());	
	}
	
}
