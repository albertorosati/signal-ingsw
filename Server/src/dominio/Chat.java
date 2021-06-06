package dominio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import database.Connector;

public class Chat {

	private int id;
	private List<Messaggio> messaggi;
	private Profilo utente1;
	private Profilo utente2;
	private int idSegnalazione;

	public Chat(int id, Profilo utente1, Profilo utente2, int idSeg) {
		this.messaggi = new ArrayList<>();
		this.utente1 = utente1;
		this.utente2 = utente2;
		this.id=id;
		this.idSegnalazione=idSeg;
	}
	
	public static Chat getChat(int idSeg, Connector conn) {
		
		//return if exists or create new
		
		int id=-1;
		PreparedStatement st;
		ResultSet rs;	
		
		try {
			st=conn.prepare("SELECT * FROM Chat WHERE segnalazione = ? ; ");
			st.setInt(1, idSeg);
			st.execute();
			
			rs = st.getGeneratedKeys();
			
			if (!rs.first()) {
				//CREATE NEW ENTRY
				st=conn.prepare("INSERT INTO Chat (segnalazione,utente1,utente2) VALUES (?,?,?) ; ");
				st.setInt(1, idSeg);
				st.setNull(2, Types.INTEGER);
				st.setNull(3, Types.INTEGER);
				st.execute();
				
				rs = st.getGeneratedKeys();
			}
			
			id = rs.getInt("id");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//utente1 e 2 da prendere con JOIN Assegnazione
		
		return new Chat(id,null,null,idSeg);
	}
	
	public void inviaMessaggio(Messaggio messaggio, Connector conn) {
		this.messaggi.add(messaggio);
		
		//Update DB
		Messaggio.getMessaggio(this.id,messaggio.getTesto(),
				Messaggio.getMittente(messaggio, conn), conn);
		
		
	}

	public void termina() {
		this.messaggi.stream().forEach(e -> messaggi.remove(e));
	}

	//-------------------GETTERS & SETTERS---------------------------------	
	
	public int getId() {
		return id;
	}

	public int getIdSegnalazione() {
		return idSegnalazione;
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
