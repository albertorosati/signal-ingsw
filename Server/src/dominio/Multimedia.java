package dominio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;



public class Multimedia {

	private File file;
	private TipoFile tipoFile;
	private String path;
	private String base64;

	public Multimedia(File file, TipoFile tipoFile) {
		this.file = file;
		this.tipoFile = tipoFile;
		
		byte[] content=null;
		try {
			content = Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.base64=Base64.getEncoder().encodeToString(content);
	}
	
	public Multimedia(String path) {
		file=new File(path);
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setTipoFile(TipoFile tipoFile) {
		this.tipoFile = tipoFile;
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
