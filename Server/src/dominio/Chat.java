package dominio;

import java.util.ArrayList;
import java.util.List;

public class Chat {
	
	private List<Messaggio> messaggi;

	public Chat() {
		this.messaggi = new ArrayList<>();
	}
	
	public void inviaMessaggio(Messaggio messaggio) {
		this.messaggi.add(messaggio);
	}
	
	public void termina() {
		this.messaggi.stream().forEach(e -> messaggi.remove(e));
	}
}
