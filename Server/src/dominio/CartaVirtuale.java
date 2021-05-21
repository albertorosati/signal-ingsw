package dominio;

public class CartaVirtuale {

	private int saldo;
	private Comune comune;
	private Profilo profilo;

	public CartaVirtuale(int saldo, Comune comune, Profilo profilo) {
		this.saldo = saldo;
		this.comune = comune;
		this.profilo = profilo;
	}

	public int aggiungiPunti(int punti) {
		saldo = this.saldo + punti;
		return saldo;
	}

	public int sottraiPunti(int punti) {
		saldo = this.saldo - punti; // controlli immagino vengano fatti a livello più alto, altrimenti poi
									// aggiungiamo qui
		return saldo;
	}

	public void azzera() {
		this.saldo = 0;
	}

	public boolean validaAcquisto(int costo) {
		return (saldo - costo > 0);
	}

	public int getSaldo() {
		return this.saldo;
	}

	public String getComune() {
		return this.comune.getNome();
	}

	public Profilo getProfilo() {
		return this.profilo;
	}
}
