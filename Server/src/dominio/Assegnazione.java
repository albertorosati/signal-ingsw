package dominio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Assegnazione {

	private LocalDate dataInizio;
	//private LocalDate dataFine;
	private MetodoPagamento metodoPagamento;
	private Segnalazione segnalazione;
	private Profilo produttore;
	private Profilo consumatore;

	public Assegnazione(Segnalazione segnalazione, Profilo produttore, Profilo consumatore) {
		this.segnalazione = segnalazione;
		this.produttore = produttore;
		this.consumatore = consumatore;
		this.dataInizio = LocalDate.now();				
	}
	
	public LocalDate getDataInizio() {
		return this.dataInizio;
	}

	public LocalDate getDataFine() {
		return this.dataFine;
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
