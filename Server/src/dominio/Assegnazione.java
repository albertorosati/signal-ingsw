package dominio;

import java.time.LocalDate;

public class Assegnazione {

	private LocalDate dataInizio;
	private LocalDate dataFine;
	private MetodoPagamento metodoPagamento;
	private Segnalazione segnalazione;
	private Profilo produttore;
	private Profilo consumatore;

	public Assegnazione(Segnalazione segnalazione, Profilo produttore, Profilo consumatore, LocalDate dataFine) {
		this.segnalazione = segnalazione;
		this.produttore = produttore;
		this.consumatore = consumatore;
		this.dataInizio = LocalDate.now();
		this.dataFine = dataFine;
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
}
