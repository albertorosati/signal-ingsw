package userHomePage;

import java.sql.SQLException;

import database.Connector;
import dominio.Profilo;
import dominio.Segnalazione;

public class UserHomePageController implements IUserHomePage {
	
	private Connector conn;
	
	public UserHomePageController() throws SQLException {
		this.conn = Connector.getInstance();
	}
	
	public Segnalazione[] getBacheca(Profilo profilo) {
		return null;
	}
	
}