package utils;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletResponse;

public class RouteManager {
	public static String getBasePath() {
		ResourceBundle rb = ResourceBundle.getBundle("config");
		return rb.getString("basePath");
	}
	public static void route(HttpServletResponse response, String url) throws IOException {
		response.sendRedirect(getBasePath() + url);
	}
}
