package servlet;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

public class Utils {
	
	public static String getReqBody(HttpServletRequest req) throws IOException {
		return req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
	}

}
