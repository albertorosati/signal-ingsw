package dominio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import database.Connector;
import exceptions.EmailNotExistingException;

public class Chat {

	private int id;
	private List<Messaggio> messaggi;
	private int idSegnalazione;
	
	//Non serve mantenere due profili. Nel caso di segnalazioni pubbliche qualsiasi gestore può scrivere in chat
	//obbligatorio salvare sempre Consumatore
	//Consumatore
	private Profilo utente1;
	
	//Produttore
	private Profilo utente2;	
	

	public Chat(int id, Profilo utente1, Profilo utente2, int idSeg) {
		this.messaggi = new ArrayList<>();
		this.utente1 = utente1;
		this.utente2 = utente2;
		this.id=id;
		this.idSegnalazione=idSeg;
	}
	
	public Chat(int id, int consumatore, int idSeg,Connector conn) throws SQLException, EmailNotExistingException {
		this.messaggi = new ArrayList<>();
		this.utente1 = Profilo.getProfiloById(conn, consumatore);
		this.utente2 = null;
		this.id=id;
		this.idSegnalazione=idSeg;
	}
	
	public static Chat getChat(int idSeg, Connector conn) throws SQLException{
		
		//return if exists or create new
		
		int id=-1, idConsumatore=-1;
		
		
		PreparedStatement st;
		ResultSet rs;	
		
		try {
			st=conn.prepare("SELECT * FROM Chat WHERE segnalazione = ? ; ");
			st.setInt(1, idSeg);
			st.execute();
			
			rs = st.getGeneratedKeys();
			
			if (!rs.first()) {
				
				//GET CONSUMATORE
				st=conn.prepare("SELECT * FROM Assegnazione WHERE segnalazione = ? ; ");
				st.setInt(1, idSeg);
				rs=st.executeQuery();
				
				idConsumatore=rs.getInt("consumatore");
				
				//CREATE NEW ENTRY
				st=conn.prepare("INSERT INTO Chat (segnalazione,utente1) VALUES (?,?) ; ");
				st.setInt(1, idSeg);
				st.setInt(2, idConsumatore);
				st.execute();
				
				rs = st.getGeneratedKeys();
			}else
				idConsumatore=rs.getInt("consumatore");
			
			id = rs.getInt("id");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//utente1 e 2 da prendere con JOIN Assegnazione
		
		Chat res;
		
		try {
			res=new Chat(id,idConsumatore,idSeg,conn);
		} catch (SQLException | EmailNotExistingException e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	public void inviaMessaggio(Messaggio messaggio, Connector conn) {
		this.messaggi.add(messaggio);
		
		//Update DB:  Messaggi <-- (idChat,mittente,testo)
		Messaggio.getMessaggio(this.id,messaggio.getTesto(),, conn);
		
		
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
