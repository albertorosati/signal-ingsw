package dominio;

import java.io.File;

public class Multimedia {

	private File file;
	private String posizione;
	private int dimensione;
	private TipoFile tipoFile;

	public Multimedia(File file, String posizione, int dimensione, TipoFile tipoFile) {
		this.file = file;
		this.posizione = posizione;
		this.dimensione = dimensione;
		this.tipoFile = tipoFile;
	}

	public File getFile() {
		return file;
	}

	public String getPosizione() {
		return posizione;
	}

	public int getDimensione() {
		return dimensione;
	}

	public TipoFile getTipoFile() {
		return tipoFile;
	}

}
