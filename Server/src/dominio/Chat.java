package dominio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import database.Connector;
import exceptions.EmailNotExistingException;
import json.Mess;

public class Chat {

	private int id;
	
	//getFromDB base on idSegnalazione 
	private List<Messaggio> messaggiLocal;
	
	//messaggi from others
	private List<Messaggio> messaggiRemote;
	private int idSegnalazione;
	
	//LIST di messaggi aggiornate solo con i nuovi messaggi, non si salvano quelli
	//passati ma li si passa alla servlet su richiesta in una struttura apposita
	
	//Alla fine dell'interazione viene fatta updateChat()
	//e vengono inseriti nel DB solo i nuovi messaggi
	
	
	//Non serve mantenere due profili. Nel caso di segnalazioni pubbliche qualsiasi gestore puo' scrivere in chat
	//obbligatorio salvare sempre Consumatore
	//Consumatore
	private Profilo utente1;
		
	//Produttore
	private Profilo utente2;	
	

	public Chat(int id, Profilo utente1, Profilo utente2, int idSeg) {
		this.messaggiLocal = new ArrayList<>();
		this.messaggiLocal = new ArrayList<>();
		this.utente1 = utente1;
		this.utente2 = utente2;
		this.id=id;
		this.idSegnalazione=idSeg;
	}
	
	public Chat(Profilo utente1, Profilo utente2, int idSeg) {
		this.messaggiLocal = new ArrayList<>();
		this.messaggiLocal = new ArrayList<>();
		this.utente1 = utente1;
		this.utente2 = utente2;
		this.id=-1;
		this.idSegnalazione=idSeg;
	}
	
	public Chat(int id, int consumatore, int idSeg,Connector conn) throws SQLException, EmailNotExistingException {
		this.messaggiLocal = new ArrayList<>();
		this.messaggiLocal = new ArrayList<>();
		this.utente1 = Profilo.getProfiloById(conn, consumatore);
		this.utente2 = null;
		this.id=id;
		this.idSegnalazione=idSeg;
	}
	
	public Mess[] returnOldMessages(int idProfile, Connector conn) {
	
		Mess[] res= null;
		List<Mess> tmp=new ArrayList<>();
		
		//Local-->right
		//Remote-->left
		
		//RESULT=
		//right  txt
		//right  txt
		//...
		//left	 txt
		//...
		
		//getFromDB --> messaggi.{Local|Remote}
		PreparedStatement st;
		ResultSet rs;
		
		//Map<String,String> tmp=new HashMap<>();
		
		try {
			//GET LOCAL MESSAGES
			st=conn.prepare("SELECT messaggio FROM Messaggi WHERE chat = ? AND mittente = ? ; ");
			st.setInt(1, this.id);
			st.setInt(2, idProfile);
			st.execute();
			
			rs = st.executeQuery();
			
			while(rs.next()) 
				tmp.add(new Mess("right",rs.getString("messaggio"),rs.getDate("datetime").toString()));
							
			//GET REMOTE MESSAGES
			st=conn.prepare("SELECT messaggio FROM Messaggi WHERE chat = ? AND mittente != ? ; ");
			st.setInt(1, this.id);
			st.setInt(2, idProfile);
			st.execute();
			
			rs = st.executeQuery();
			
			while(rs.next()) 
				tmp.add(new Mess("left",rs.getString("messaggio"),rs.getDate("datetime").toString()));
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		res=(Mess[]) tmp.toArray();
		return res;
	}
	
	public static Chat getChat(int idSeg,int idProfile, Connector conn) throws SQLException{
		
		//idProfile --> Local
				
		int id=-1, idConsumatore=-1;
		
		
		PreparedStatement st;
		ResultSet rs;	
		
		try {
			st=conn.prepare("SELECT * FROM Chat WHERE segnalazione = ? ; ");
			st.setInt(1, idSeg);
			st.execute();
			
			rs = st.executeQuery();
				
			//return if exists or create new
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
			}else {
				idConsumatore=rs.getInt("consumatore");				
			}
			id = rs.getInt("id");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//utente1 e 2 da prendere con JOIN Assegnazione
		//utente2 puo' rimanere null per i motivi di cui sopra
		Chat res=null;
		
		try {
			res=new Chat(id,idConsumatore,idSeg,conn);
		} catch (SQLException | EmailNotExistingException e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	public boolean inviaMessaggio(String messaggio, int idMittente, Connector conn) {
		
		/*WEB-SOCKET
		if(idMittente==this.utente1.getId())
			this.messaggiLocal.add(messaggio);
		else
			this.messaggiRemote.add(messaggio);
		
		
		//Update DB:  Messaggi <-- (idChat,mittente,testo)  al momento della chiusura della pagina
		//da parte di entrambi gli host
		*/
		
		//POLLING
		PreparedStatement st;
		ResultSet rs;
		boolean esito=true;
		
		try {
			st=conn.prepare("INSERT INTO Messaggi (chat, mittente, messaggio) VALUES (?,?,?) ; ");
			st.setInt(1, this.id);
			st.setInt(2, idMittente);
			st.setString(3, messaggio);
			st.execute();		
		}catch(SQLException e) {
			esito=false;
		}
		
		return esito;
	}
	
	private void updateDB(Messaggio messaggio, Connector conn) {
		PreparedStatement st;
		ResultSet rs;
		
		
		List<Messaggio> merge=new ArrayList<>();
		merge.addAll(this.messaggiLocal);
		merge.addAll(this.messaggiRemote);
		
		//st=conn.prepare("INSERT INTO SET ")
		
	}

	public void termina() {
		this.messaggiLocal.stream().forEach(e -> messaggiLocal.remove(e));
		this.messaggiRemote.stream().forEach(e -> messaggiRemote.remove(e));
	}
	
	public String[] getMessaggi(int idProfilo) {
		
		if(idProfilo==this.getUtente1().getId())
			return (String[]) this.messaggiLocal.toArray();
		else
			return (String[]) this.messaggiRemote.toArray();
					
	}

	//-------------------GETTERS & SETTERS---------------------------------	
	
	public int getId() {
		return id;
	}

	public int getIdSegnalazione() {
		return idSegnalazione;
	}

	public List<Messaggio> getAllMessaggi() {
		List<Messaggio> res=new ArrayList<>();
		res.addAll(this.messaggiLocal);
		res.addAll(this.messaggiRemote);
		
		return res;
	}

	public Profilo getUtente1() {
		return utente1;
	}

	public Profilo getUtente2() {
		return utente2;
	}
}
