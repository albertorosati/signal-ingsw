package dominio;

public class Punti implements MetodoPagamento {

	private Integer importo;

	public Punti(int importo) {
		super();
		this.importo = importo;
	}

	public Integer getImporto() {
		return importo;
	}
}
