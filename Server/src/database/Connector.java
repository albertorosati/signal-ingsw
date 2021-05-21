package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connector implements AutoCloseable {

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

	public void query() {
		try {
			PreparedStatement st = conn.prepareStatement("INSERT INTO (id, person) VALUES (?,?)");
			st.setString(1, "1");
			st.setString(2, "alb");
			// per query di insert, update, delete si usa ->
			st.executeUpdate();
			// per query di select si usa ->
			ResultSet result = st.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws Exception {
		conn.close();
	}

}
