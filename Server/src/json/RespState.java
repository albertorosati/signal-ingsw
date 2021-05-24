package json;

import com.google.gson.annotations.SerializedName;

public enum RespState {

	@SerializedName("success")
	SUCCESS("success"),

	@SerializedName("wrong_password")
	WRONG_PASSWORD("wrong_password"),

	@SerializedName("user_not_existing")
	USER_NOT_EXISTING("user_not_existing"),

	@SerializedName("not_verified")
	USER_NOT_VERIFIED("not_verified"),

	@SerializedName("suspended")
	USER_SUSPENDED("suspended"),

	@SerializedName("user_already_existing")
	USER_ALREADY_EXISTING("user_already_existing"),

	@SerializedName("cooldown")
	COOLDOWN("cooldown"),

	@SerializedName("error")
	ERROR("error"),

	@SerializedName("failure")
	FAILURE("failure");

	private String state;

	private RespState(String state) {
		this.state = state;
	}

	public String toString() {
		return this.state;
	}

}
