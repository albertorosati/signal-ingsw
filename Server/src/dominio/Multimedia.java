package dominio;

import java.io.File;

public class Multimedia {

	private File file;
	private String posizione;
	private TipoFile tipoFile;

	public Multimedia(File file, String posizione, TipoFile tipoFile) {
		this.file = file;
		this.posizione = posizione;
		this.tipoFile = tipoFile;
	}

	public File getFile() {
		return file;
	}

	public String getPosizione() {
		return posizione;
	}

	public TipoFile getTipoFile() {
		return tipoFile;
	}

}
