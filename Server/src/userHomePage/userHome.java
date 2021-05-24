package userHomePage;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import database.Connector;

import dominio.Segnalazione;
import dominio.TipoBacheca;
import dominio.Profilo;

public class userHome implements IUserHomePage {
	
	private Connector conn;
	
	public userHome() throws SQLException {
		this.conn = Connector.getInstance();
	}
	
	public Segnalazione[] getBacheca(Profilo profilo) {
		//....
	}
	
}