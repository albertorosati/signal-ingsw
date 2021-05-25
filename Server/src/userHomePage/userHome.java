package userHomePage;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import database.Connector;

import dominio.Segnalazione;
import dominio.TipoBacheca;
import dominio.Profilo;

public class userHomePageController implements IUserHomePage {
	
	private Connector conn;
	
	public userHomePageController() throws SQLException {
		this.conn = Connector.getInstance();
	}
	
	public static Segnalazione[] getBacheca(Profilo profilo) {
		//....
	}
	
}