package dominio;

public class Posizione {

	private double latitudine;
	private double longitudine;

	public Posizione(double latitudine, double longitudine) {
		this.latitudine = latitudine;
		this.longitudine = longitudine;
	}

	public double getLatitudine() {
		return latitudine;
	}

	public double getLongitudine() {
		return longitudine;
	}
}
