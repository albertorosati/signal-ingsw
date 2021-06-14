package handshake;

import java.io.File;
import java.sql.SQLException;

import dominio.Chat;
import dominio.Profilo;
import dominio.Segnalazione;

public interface IHandshake {
	public void sendMessage(int idSeg, Profilo mittente, String mex) throws SQLException;

	public void sendMedia(Chat chat, Profilo mittente, File file) throws SQLException;

	public String[][] getMessages(Segnalazione segnalazione, Profilo user) throws SQLException;

}
