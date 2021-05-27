package account;

import dominio.Profilo;

public interface ISospendi {
	public void sospendi(Profilo p);

	public void inviaEmail(Profilo p);
	
	//attiva?
}
