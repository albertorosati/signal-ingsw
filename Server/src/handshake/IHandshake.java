package handshake;

import java.io.File;

import dominio.Chat;
import dominio.Segnalazione;

public interface IHandshake {
	public void sendMessage(Chat chat, String mex);

	public void sendMedia(Chat chat, File file);

	public Chat getMessages(Segnalazione segnalazione);
}
