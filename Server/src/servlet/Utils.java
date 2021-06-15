package servlet;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.JsonSyntaxException;

import json.JsonHandler;
import json.Response;

public class Utils {
	
	public static String getReqBody(HttpServletRequest req) throws IOException {
		return req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
	}
	
	public static Response getResponseByReq(HttpServletRequest req) throws IOException {
		return JsonHandler.getInstance().getGson().fromJson(Utils.getReqBody(req), Response.class);
	}

}
