package handshake;

import java.io.File;
import java.sql.SQLException;

import dominio.Chat;
import dominio.Profilo;
import dominio.Segnalazione;

public interface IHandshake {
	public void sendMessage(int idSeg, String emailMittente, String mex) throws SQLException;

	public void sendMedia(Chat chat, Profilo mittente, File file) throws SQLException;

	public String getMessages(int idSegnalazione, String userEmail) throws SQLException;

}
