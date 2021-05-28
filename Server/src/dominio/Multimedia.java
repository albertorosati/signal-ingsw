package dominio;

import java.io.File;

public class Multimedia {

	private File file;
	private TipoFile tipoFile;
	private String path;

	public Multimedia(File file, TipoFile tipoFile) {
		this.file = file;
		this.tipoFile = tipoFile;
	}
	
	public Multimedia(String path) {
		file=new File(path);
	}

	public File getFile() {
		return file;
	}

	public TipoFile getTipoFile() {
		return tipoFile;
	}
	
	public void setPath(String p) {
		this.path=p;
	}
	
	public String getPath() {
		return this.path;
	}
}
