package json;

import dominio.Profilo;

public class Profile {
	
	private String nome;
	private String cognome;
	private float reputazione;
	private String role;
	
	public Profile() {
		super();
	}
	
	public static Profile toProfile(Profilo pr) {
		Profile res=new Profile();
		
		res.setNome(pr.getNome());
		res.setCognome(pr.getCognome());
		res.setReputazione(pr.getValutazione());
		res.setRole(pr.getRuolo().toString());
		
		return res;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public float getReputazione() {
		return reputazione;
	}

	public void setReputazione(float reputazione) {
		this.reputazione = reputazione;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
		

}
