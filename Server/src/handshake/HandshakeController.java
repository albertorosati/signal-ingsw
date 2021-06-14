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
	
	public HandshakeController(){
		try {
			this.conn = Connector.getInstance();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMessage(int idSeg, String emailMittente, String mex) throws SQLException {
		Chat chat;
		Profilo mittente=null;
		try {
			mittente = Profilo.getProfiloByEmail(conn, emailMittente);
		} catch (SQLException | EmailNotExistingException e) {
			e.printStackTrace();
		}
		chat=Chat.getChat(idSeg, mittente.getId(), conn);
		chat.inviaMessaggio(mex, mittente.getId(), conn);
	}

	@Override
	public void sendMedia(Chat chat, Profilo mittente, File file) {
		// ...
	}

	@Override
	public String[][] getMessages(Segnalazione segnalazione, Profilo user) throws SQLException {
		String[][] res;
		Chat ch;
		
		ch=Chat.getChat(segnalazione.getId(),user.getId(),conn);
		res=ch.returnOldMessages(user.getId(), conn);
		
		return res;		
	}

}
