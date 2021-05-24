package dominio;

import java.io.File;

public class Multimedia {

	private File file;
	private TipoFile tipoFile;

	public Multimedia(File file, TipoFile tipoFile) {
		this.file = file;
		this.tipoFile = tipoFile;
	}

	public File getFile() {
		return file;
	}

	public TipoFile getTipoFile() {
		return tipoFile;
	}

}
