package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Connector implements AutoCloseable {

	private Connection conn;
	private final String HOST = "ingsft-signal.duckdns.org";
	private final String USERNAME = "admin";
	private final String PASSWORD = "password";
	private final String DATABASE = "applicazione";
	private final String URL = "jdbc:mariadb://" + HOST + ":3306/" + DATABASE + "?user=" + USERNAME + "&password="
			+ PASSWORD;

	private static Connector instance;

	private Connector() throws SQLException {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		conn = DriverManager.getConnection(URL);
	}

	public PreparedStatement prepare(String query) throws SQLException {
		return conn.prepareStatement(query);
	}

	// Restituisce la chiave appena generata
	public PreparedStatement prepareReturn(String query) throws SQLException {
		return conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	}

	@Override
	public void close() throws Exception {
		conn.close();
	}

	public static Connector getInstance() throws SQLException {
		if (instance == null)
			instance = new Connector();
		return instance;
	}

}
