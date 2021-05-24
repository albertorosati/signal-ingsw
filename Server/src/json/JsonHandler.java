package json;

import com.google.gson.Gson;

public class JsonHandler {

	private Gson gson = new Gson();
	private static JsonHandler instance;

	public Gson getGson() {
		return this.gson;
	}

	public static JsonHandler getInstance() {
		if (instance == null)
			instance = new JsonHandler();
		return instance;
	}

}
