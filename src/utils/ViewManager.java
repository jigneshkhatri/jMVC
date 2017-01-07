package utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewManager {
	public static void showView(HttpServletRequest request, HttpServletResponse response, String viewName) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/" + viewName).forward(request, response);
	}
}
