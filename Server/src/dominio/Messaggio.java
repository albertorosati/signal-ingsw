package dominio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Connector;
import exceptions.EmailNotExistingException;

public class Messaggio {

	private int id;
	private String testo;
	private Profilo from;
	private int idChat;
	private int idMittente;

	public Messaggio(int id, String testo, Profilo from, int idChat) {
		this.id=id;
		this.testo = testo;
		this.from = from;
		this.idChat=idChat;
	}
	
	public static Messaggio getMessaggio(int idChat, String text, Profilo mittente,Connector conn) {
		int id=-1;
		
		//Inserto into DB
		PreparedStatement st;
		ResultSet rs;	
		
		try {
			st=conn.prepare("INSERT INTO Messaggi (chat, mittente, messaggio) VALUES (?,?,?) ; ");
			st.setInt(1, idChat);
			st.setInt(2, mittente.getId());
			st.setString(3, text);
			st.execute();
			
			rs = st.getGeneratedKeys();
			
			if (!rs.first())
				throw new SQLException("ERROR");

			id = rs.getInt("id");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		return new Messaggio(id,text,mittente,idChat);		
	}

	public String getTesto() {
		return testo;
	}

	public Profilo getFrom() {
		return from;
	}

	public int getId() {
		return id;
	}

	public int getIdChat() {
		return idChat;
	}
	
	public int getIdMittente() {
		return this.idMittente;
	}
	
	private static Profilo getMittente(Messaggio mess, Connector conn) {
		Profilo res=null;
		
		//Get from Segnalazione where id=this.idSeg
		int idMittente=-1;
		
		try {
			res=Profilo.getProfiloById(conn, mess.getIdMittente());
		} catch (SQLException | EmailNotExistingException e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	
}
