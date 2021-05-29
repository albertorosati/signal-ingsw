package dominio;

public interface MetodoPagamento {

	public <T extends Number> T getImporto();
	
	public String getName();
}
