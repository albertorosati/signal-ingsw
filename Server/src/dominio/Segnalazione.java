package dominio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import database.Connector;

public class Segnalazione {

	private int id;
	private String titolo;
	private String descrizione;
	private LocalDateTime timestampCreazione;
	private LocalDateTime timestampAssegnazione;
	private Duration durataAssegnazione;
	private List<String> tags;
	private boolean visible;
	private Stato stato;
	private Comune comune;
	private Posizione posizione;
	private TipoBacheca[] tipoBacheca;
	private List<Profilo> richiedenti;
	private Profilo produttore;
	private Profilo consumatore;
	private Chat chat;
	private Assegnazione assegnazione; // TODO non mettere nel costruttore
	private String imgSrc;
	private boolean _public;
	
	private Connector connector;

	public Segnalazione(int id, String titolo, String descrizione, LocalDateTime timestampCreazione, List<String> tags,
			boolean visible, Stato stato, Posizione posizione, Comune comune, Profilo produttore, Profilo consumatore,
			Chat chat, String imgSrc, boolean _public) {
		this.id = id;
		this.titolo = titolo;
		this.descrizione = descrizione;
		this.timestampCreazione = timestampCreazione;
		this.tags = tags;
		this.visible = visible;
		this.stato = stato;
		this.posizione = posizione;
		this.produttore = produttore;
		this.consumatore = consumatore;
		this.chat = chat;
		this.imgSrc = imgSrc;
		this._public = _public;
		
		try {
			this.connector=Connector.getInstance();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Segnalazione (String titolo, String descrizione, List<String> tags,
			Posizione posizione, String comune, Profilo produttore, String imgSrc) {
				
		this.visible=true;
		this.stato=Stato.IN_APPROVAZIONE;
		this.consumatore=null;
		this.chat=null;
		
		this.titolo=titolo;
		this.descrizione=descrizione;
		this.tags=tags;
		this.posizione=posizione;
		this.comune=new Comune(comune);
		this.produttore=produttore;
		this.imgSrc=imgSrc;
	}
	
	//--------------------------------------------------------------------------------------------------------------------
	public static Segnalazione getById(Connector conn, int id) throws SQLException {
		PreparedStatement ps = conn.prepare(
				"SELECT S.*, GROUP_CONCAT(T.nome) tags FROM Segnalazioni S JOIN Tag T ON T.segnalazione=S.id WHERE S.id = ?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		if (!rs.first())
			throw new IllegalArgumentException("La segnalazione non esiste");

		return new Segnalazione(rs.getInt("id"), rs.getString("titolo"), rs.getString("descrizione"),
				rs.getTimestamp("timestamp").toLocalDateTime(), Arrays.asList(rs.getString("tags").split(",")),
				rs.getBoolean("visibile"), Stato.values()[rs.getInt("stato")],
				new Posizione(rs.getDouble("lat"), rs.getDouble("lon")), null, null, null, null,
				rs.getString("imageSrc"), rs.getBoolean("pubblica"));
	}
	
	public static void insert(Connector conn, Segnalazione s) {
		try {
			Segnalazione.of(conn, s.getAutore().getId(), s.getTitolo(), s.getDescrizione(), s.getTags(), s.getPosizione(),
					s.getProduttore(), s.getImage(), s.getComune().getNome());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Segnalazione of(Connector conn, int autore, String titolo, String descrizione, List<String> tags,
			Posizione posizione, Profilo produttore, String imgSrc, String comune) throws SQLException {
		PreparedStatement st = conn.prepareReturn(
				"INSERT INTO Segnalazioni (autore, titolo, descrizione, imageSrc, lat, lon,comune) VALUES (?,?,?,?,?,?,?)");

		st.setInt(1, autore);
		st.setString(2, titolo);
		st.setString(3, descrizione);
		st.setString(4, imgSrc);
		st.setDouble(5, posizione.getLatitudine());
		st.setDouble(6, posizione.getLongitudine());
		st.setString(7, comune);
		st.executeUpdate();

		ResultSet rs = st.getGeneratedKeys();

		if (!rs.first())
			throw new SQLException("Errore interno");

		int id = rs.getInt("id");

		for (String tag : tags) {
			st = conn.prepare("INSERT INTO Tag (nome, segnalazione) VALUES (?,?)");
			st.setString(1, tag);
			st.setInt(2, id);
			st.execute();
		}

		return Segnalazione.getById(conn, rs.getInt("id"));
	}
	
	//--------------------------------------------------------------------------------------------------------------------

	
	//-----------------------------------------------METHODS-------------------------------------------------------------
	
	public Stato impostaStato(Stato stato) {
		PreparedStatement ps;
		try {
			ps = connector.prepare("UPDATE Segnalazioni SET stato= ?  WHERE id = ?");
			ps.setInt(1,stato.ordinal());
			ps.setInt(2, id);
			ResultSet rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		this.stato = stato;
			
		return this.stato;
	}

	public boolean is_public() {
		return _public;
	}
	
	public void modificaTag(String old, String niu) {
		
		int pos=this.tags.indexOf(old);
		
		if(pos>0) {
			this.tags.set(pos, niu); 
				
			PreparedStatement ps;
			try {
				ps = connector.prepare("UPDATE Tag SET nome = ?  WHERE id = ? AND nome = ? ; ");
				ps.setString(1,niu);
				ps.setInt(2, this.id);
				ps.setString(3,old);
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
	}

	
	public List<String> aggiungiTag(String tag) {
		PreparedStatement ps;
		try {
			ps = connector.prepare("UPDATE Tag SET nome = ?  WHERE id = ?");
			ps.setString(1,tag);
			ps.setInt(2, this.id);
			ResultSet rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
				
		this.tags.add(tag);
		return this.tags;
	}

	public void aggiungiRichiedente(Profilo richiedente) {
		PreparedStatement ps;
		try {
			
			ps = connector.prepare("UPDATE Proposte SET utente = ?  WHERE segnalazione = ?");
			ps.setString(1,richiedente.getIdentificatore());
			ps.setInt(2, this.id);
			ResultSet rs = ps.executeQuery();
			
			this.impostaStato(Stato.RICHIESTA_PRESA_IN_CARICO);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		this.richiedenti.add(richiedente);
	}

	public void assegna(Profilo profilo) {
		this.consumatore = profilo;
		this.timestampAssegnazione = LocalDateTime.now();
		this.assegnazione = new Assegnazione(this.id, this.produttore, this.consumatore);
		
		PreparedStatement ps;
		try {
					
			//SET SEGNALAZIONE
			ps=connector.prepare("UPDATE Segnalazioni SET (assegnatario,timestampAssegnazione) = (?,?) "
					+ "WHERE id = ? ;");
			ps.setInt(1, this.assegnazione.getId());	
			ps.setString(2, this.timestampAssegnazione.toString());
			ps.setInt(3, this.id);
			
			ps.execute();		
			
			//SET Segnalazione invisible
			this.setVisibility(false);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void inserisciInBacheca() {
		//check: gestore deve aver prima fatto setPublic o private
		
		try {
			this.add2Bacheca();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void rifiuta(Profilo profilo) {
				
		PreparedStatement ps;
		try {
					
			//UPDATE PROPOSTE
			ps=connector.prepare("DELETE * FROM Proposte WHERE (segnalazione,utente) = (?,?) ;");
			ps.setInt(1, this.id);
			ps.setInt(2, profilo.getId());
			
			ps.execute();		
			
			//check if prop==0
			ps=connector.prepare("SELECT COUNT(*) AS Pippo FROM Proposte WHERE segnalazione = ? ;");
			ps.setInt(1, this.id);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.getInt("Pippo")==0)	
				this.impostaStato(Stato.DISPONIBILE);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void revocaAssegnazione() {
		PreparedStatement ps;
		
		this.assegnazione=null;
		
		try {
			//SET ASSEGNAZIONE
			ps=connector.prepare("DELETE FROM Assegnazione WHERE segnalazione = ?;");
			ps.setInt(1, this.id);
			ps.execute();			
						
			//SET SEGNALAZIONE			
			ps=connector.prepare("UPDATE Segnalazioni SET (assegnatario,timestampAssegnazione) = (?,?) "
					+ "WHERE id = ? ;");
			ps.setNull(1,java.sql.Types.INTEGER);	
			ps.setNull(2, java.sql.Types.VARCHAR);
			ps.setInt(3, this.id);			
			ps.execute();	
			
			//cosa fare con le vecchie proposte ??
			//le scartiamo tutte al momento della presa in carico ?? io direi di si'
			//SET PROPOSTE
			ps=connector.prepare("DELETE FROM Proposte WHERE segnalazione = ?;");
			ps.setInt(1, this.id);
			ps.execute();
			
			//RESET VISIBLE ON BACHECA
			this.setVisibility(true);
			
			
			this.impostaStato(Stato.DISPONIBILE);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void termina() {
		
		PreparedStatement ps;
		try {
			//SET ASSEGNAZIONE
			ps=connector.prepare("DELETE FROM Assegnazione WHERE segnalazione = ? ;");
			ps.setInt(1, this.id);
			ps.execute();
			
			//DELETEBACHECA
			this.deleteBacheca();
								
			this.setBacheca(null);
			this.impostaStato(Stato.CONCLUSA);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean setPay(MetodoPagamento pay) {
		boolean res=true;
		
		//check if punti --> puntiDisponibili??
		if(pay.getName().compareTo("punti")==0) 			
			if(this.produttore.getPoint(this.comune)<(int)pay.getImporto())
				return false;
		
		//update Point(comune)
		this.assegnazione.setMetodoPagamento(pay);
		this.impostaStato(Stato.IN_CORSO);
		
		return res;
	}

	public Chat avviaChat() {
		this.chat = new Chat(produttore, consumatore,this.id);
		return this.chat;
	}
	
	//--------------------------SET BACHECA-----------------------------------------
	
		private void add2Bacheca() throws SQLException {
			PreparedStatement ps;
			
			if(this.is_public()) {
				ps=connector.prepare("INSERT INTO BachecaPROConv (idSeg) = ? ;");
				ps.setInt(1, this.id);
				ps.execute();
			}else {
				ps=connector.prepare("INSERT INTO BachecaBase (idSeg) = ? ;");
				ps.setInt(1, this.id);
				ps.execute();
			}
			
			//add BACHECA PRO
			ps=connector.prepare("INSERT INTO BachecaPRO (idSeg) = ? ;");
			ps.setInt(1, this.id);
			ps.execute();
		}
		
		private void deleteBacheca() throws SQLException {
			PreparedStatement ps;
			
			if(this.is_public()) {
				//delete pro_conv			
				ps=connector.prepare("DELETE FROM BachecaPROConv WHERE idSeg = ? ;");
				ps.setInt(1, this.id);
				ps.execute();			
				
			}else {
				//delete and base
				ps=connector.prepare("DELETE FROM BachecaBase WHERE idSeg = ? ;");
				ps.setInt(1, this.id);
				ps.execute();
			}
			
			//delete pro
			ps=connector.prepare("DELETE FROM BachecaPRO WHERE idSeg = ? ;");
			ps.setInt(1, this.id);
			ps.execute();
		}
		
		private void setVisibility(boolean vis) throws SQLException {
			PreparedStatement ps;
			
			if(this.is_public()) {
				ps=connector.prepare("UPDATE BachecaPROConv SET visible = ? WHERE idSeg = ? ;");
				ps.setBoolean(1, vis);
				ps.setInt(2, this.id);
				ps.execute();
			}else {
				ps=connector.prepare("UPDATE BachecaBase SET visible = ? WHERE idSeg = ? ;");
				ps.setBoolean(1, vis);
				ps.setInt(2, this.id);
				ps.execute();
			}
			
			//add BACHECA PRO
			ps=connector.prepare("UPDATE BachecaPRO SET visible = ? WHERE idSeg = ? ;");
			ps.setBoolean(1, vis);
			ps.setInt(2, this.id);
			ps.execute();
		}
		
	//-----------------------------------------------------------------------------
		
	//-------------------------------------------------------------------------------------------------------------------
	//-------------------------------------------GETTERS & SETTERS-------------------------------------------------------

	public int getId() {
		return id;
	}

	public Chat getChat() {
		return chat;
	}

	public String getTitolo() {
		return titolo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public LocalDateTime getTimestampCreazione() {
		return timestampCreazione;
	}

	public List<String> getTags() {
		return tags;
	}

	public Profilo getAutore() {
		return produttore;
	}

	public TipoBacheca[] getTipoBacheca() {
		return tipoBacheca;
	}
	
	public void setBacheca(TipoBacheca[] b) {
		this.tipoBacheca=b;
	}

	public Stato getStato() {
		return stato;
	}

	public String getImage() {
		return imgSrc;
	}

	public Posizione getPosizione() {
		return posizione;
	}

	public Comune getComune() {
		return comune;
	}

	public List<Profilo> getRichiedenti() {
		return richiedenti;
	}

	public Duration getDurataAssegnazione() {
		return durataAssegnazione;
	}

	public LocalDateTime getTimestampAssegnazione() {
		return timestampAssegnazione;
	}

	public boolean isVisible() {
		return visible;
	}

	public Profilo getProduttore() {
		return produttore;
	}

	public Profilo getConsumatore() {
		return consumatore;
	}

	public Assegnazione getAssegnazione() {
		return assegnazione;
	}

	public void setPublic(boolean val) {
		this._public = val;
		
		PreparedStatement ps;
		
		try {
			ps=connector.prepare("UPDATE Segnalazioni SET pubblica = ? WHERE id = ? ;");
			ps.setBoolean(1, val);
			ps.setInt(2, this.id);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
