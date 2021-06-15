package json;

public class Mess {

	private String direction;
	private String messaggio;
	private String timestamp;
	
	
	public Mess() {
		super();
	}
	
	public Mess(String direction, String text) {
		super();
		this.direction=direction;
		this.messaggio=text;
	}


	public String getDirection() {
		return direction;
	}


	public void setDirection(String direction) {
		this.direction = direction;
	}


	public String getMessaggio() {
		return messaggio;
	}


	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}


	public String getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	

	
}
