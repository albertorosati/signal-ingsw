package dominio;

import com.google.gson.annotations.SerializedName;

public enum RuoloUtente {
	@SerializedName("0")
	BASE(0),

	@SerializedName("1")
	PRO(1),

	@SerializedName("2")
	PRO_CONVENZIONATO(2),

	@SerializedName("3")
	GESTORE(3),

	@SerializedName("4")
	MODERATORE(4),

	@SerializedName("5")
	AMMINISTRATORE(5),

	@SerializedName("6")
	GESTORE_SICUREZZA(6);

	private int id;

	private RuoloUtente(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}
}
