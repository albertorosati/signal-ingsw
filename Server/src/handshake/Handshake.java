package handshake;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Connector;
import dominio.Chat;
import dominio.Profilo;
import dominio.Segnalazione;

public class Handshake implements IHandshake {

	private Connector conn = Connector.getIstance();

	@Override
	public void sendMessage(Chat chat, Profilo mittente, String mex) throws SQLException {
		PreparedStatement st = conn.prepare("INSERT INTO Messaggi (chat, mittente, messaggio) VALUES (?,?, ?)");
		// chat.getId()
		st.setInt(1, 11223);
		// mittente.getId()
		st.setInt(2, 4534);
		st.setString(3, mex);

	}

	@Override
	public void sendMedia(Chat chat, Profilo mittente, File file) {
		// TODO Auto-generated method stub

	}

	@Override
	public Chat getMessages(Segnalazione segnalazione) throws SQLException {
		PreparedStatement st = conn.prepare("SELECT * FROM Chat WHERE segnalazione = ?");
		// segnalazione.getId()
		st.setInt(1, 2342394);
		ResultSet rs = st.executeQuery();

		if (!rs.first())
			return null;
		
		//Chat chat = new Chat(rs.getInt("utente1"), rs.getInt("utente2"));
		
		return null;
	}

}
