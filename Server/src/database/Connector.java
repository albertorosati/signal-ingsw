package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
		conn = DriverManager.getConnection(URL);
	}

	public PreparedStatement prepare(String query) throws SQLException {
		return conn.prepareStatement(query);
	}

	@Override
	public void close() throws Exception {
		conn.close();
	}

	public static Connector getInstance() throws SQLException {
		if (instance == null)
			try {
				instance = new Connector();
			} catch (SQLException e) {
				throw new SQLException();
			}
		return instance;
	}

}
