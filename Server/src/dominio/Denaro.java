package dominio;


public class Denaro implements MetodoPagamento {

	private Float importo;

	public Denaro(float importo) {
		this.importo = importo;
	}

	public Float getImporto() {
		return importo;
	}
}
