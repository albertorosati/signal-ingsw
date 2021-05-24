package dominio;

import com.google.gson.annotations.SerializedName;

public enum RuoloUtente {
	@SerializedName("0")
	BASE(0),

	@SerializedName("0")
	PRO(1),

	@SerializedName("0")
	PRO_CONVENZIONATO(2),

	@SerializedName("0")
	GESTORE(3),

	@SerializedName("0")
	MODERATORE(4),

	@SerializedName("0")
	AMMINISTRATORE(5),

	@SerializedName("0")
	GESTORE_SICUREZZA(6);

	private int id;

	private RuoloUtente(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}
}
