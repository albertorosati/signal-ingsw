package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import autenticazione.ILogin;
import autenticazione.Login;
import json.Response;


public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 129509529672956551L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (!req.getParameterMap().containsKey("email") || !req.getParameterMap().containsKey("password")) {
			return;
		}
		
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		ILogin loginController = new Login();
		Response result = new Response();
		try {
			result = loginController.autentica(email, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		resp.getWriter().print(result);
		
		super.doPost(req, resp);
	}
	
	public static void main(String[] args) throws SQLException {
		ILogin loginController = new Login();
		Response result = loginController.autentica("prova@mail.com", "password");
		System.out.println(result.toJson());
	}

}
