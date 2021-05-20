package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

	private Connection conn;
	private final String HOST = "signal-ingsft.duckdns.org";
	private final String USERNAME = "admin";
	private final String PASSWORD = "password";
	private final String DATABASE = "appdb";
	private final String URL = "jdbc:mariadb://" + HOST + ":3306/" + DATABASE + "?user=" + USERNAME + "&password="
			+ PASSWORD;

	public Connector() {
		try {
			conn = DriverManager.getConnection(URL);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
