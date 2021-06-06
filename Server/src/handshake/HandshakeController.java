package handshake;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Connector;
import dominio.Chat;
import dominio.Messaggio;
import dominio.Profilo;
import dominio.Segnalazione;
import exceptions.EmailNotExistingException;

public class HandshakeController implements IHandshake {

	private Connector conn;
	
	public HandshakeController() throws SQLException {
		this.conn = Connector.getInstance();
	}

	@Override
	public void sendMessage(Chat chat, Profilo mittente, String mex) throws SQLException {
		chat.inviaMessaggio(Messaggio.getMessaggio(chat.getId(), mex, mittente, conn),conn);
	}

	@Override
	public void sendMedia(Chat chat, Profilo mittente, File file) {
		// ...
	}

	@Override
	public Chat getMessages(Segnalazione segnalazione) throws SQLException {
		Chat res;
		
		res=Chat.getChat(segnalazione.getId(),conn);
		
		return res;
	}

}
