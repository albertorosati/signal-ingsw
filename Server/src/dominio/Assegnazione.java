package dominio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import database.Connector;

public class Assegnazione {
	private int id;
	private LocalDate dataInizio;
	//private LocalDate dataFine;
	private MetodoPagamento metodoPagamento;
	private int segnalazione;
	private Profilo produttore;
	private Profilo consumatore;
	
	private Connector connector;

	public Assegnazione(int segnalazione, Profilo produttore, Profilo consumatore) {
		this.segnalazione = segnalazione;
		this.produttore = produttore;
		this.consumatore = consumatore;
		this.dataInizio = LocalDate.now();		
		
		PreparedStatement ps;
		
		try {
			this.connector=Connector.getInstance();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			//SET ASSEGNAZIONE
			ps=connector.prepareReturn("INSERT INTO Assegnazione (inizio,segnalazione,produttore,consumatore) "
					+ "VALUES (?,?,?,?) ;");
			ps.setString(1,this.dataInizio.toString());		
			ps.setInt(2, this.segnalazione);
			ps.setString(3, this.produttore.getIdentificatore());
			ps.setString(4, this.consumatore.getIdentificatore());
			
			ps.execute();			
			
			ResultSet idAss=ps.getGeneratedKeys();
			this.id=idAss.getInt("id");		
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setMetodoPagamento(MetodoPagamento mp) {
		this.metodoPagamento=mp;
		PreparedStatement ps;
		
			
		try {
			//SET ASSEGNAZIONE
			ps=connector.prepareReturn("UPDATE Assegnazione (metodoPagamento,valorePagamento) "
					+ "VALUES (?,?) ;");
			ps.setString(1,mp.getName());		
			ps.setFloat(2, mp.getImporto());
			
			ps.execute();			
			
			ResultSet idAss=ps.getGeneratedKeys();
			this.id=idAss.getInt("id");		
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
		
	public int getId() {
		return this.id;
	}
	
	public LocalDate getDataInizio() {
		return this.dataInizio;
	}

	public boolean isValida() {
		return metodoPagamento.getImporto().intValue() > 0;
	}

	public MetodoPagamento getMetodoPagamento() {
		return metodoPagamento;
	}

	public int getSegnalazione() {
		return this.segnalazione;
	}

	public Profilo getProduttore() {
		return produttore;
	}

	public Profilo getConsumatore() {
		return consumatore;
	}
}
