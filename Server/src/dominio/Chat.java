package dominio;

import java.util.ArrayList;
import java.util.List;

public class Chat {

	private List<Messaggio> messaggi;
	private Profilo utente1;
	private Profilo utente2;

	public Chat(Profilo utente1, Profilo utente2) {
		this.messaggi = new ArrayList<>();
		this.utente1 = utente1;
		this.utente2 = utente2;
	}

	public void inviaMessaggio(Messaggio messaggio) {
		this.messaggi.add(messaggio);
	}

	public void termina() {
		this.messaggi.stream().forEach(e -> messaggi.remove(e));
	}

	public List<Messaggio> getMessaggi() {
		return messaggi;
	}

	public Profilo getUtente1() {
		return utente1;
	}

	public Profilo getUtente2() {
		return utente2;
	}
}
