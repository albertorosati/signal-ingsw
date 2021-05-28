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
			ps=connector.prepare("INSERT INTO Assegnazione (inizio,segnalazione,produttore,consumatore) "
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
		
	public int getId() {
		return this.id;
	}
	
	public LocalDate getDataInizio() {
		return this.dataInizio;
	}

	public boolean isValida() {
		return this.dataInizio.isBefore(this.dataFine) && metodoPagamento.getImporto().intValue() > 0;
	}

	public MetodoPagamento getMetodoPagamento() {
		return metodoPagamento;
	}

	public Segnalazione getSegnalazione() {
		return segnalazione;
	}

	public Profilo getProduttore() {
		return produttore;
	}

	public Profilo getConsumatore() {
		return consumatore;
	}
}
