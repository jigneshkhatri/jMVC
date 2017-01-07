package utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionManager {
	
	public static HttpSession sessionExists(HttpServletRequest request, String sessionName) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute(sessionName) == null) return null;
		else return session;
	}
	
	public static void setSession(HttpServletRequest request, String sessionName, Object sessionValue) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute(sessionName, sessionValue);
	}
	
	public static void unsetSession(HttpServletRequest request, String sessionName) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute(sessionName) != null) session.removeAttribute(sessionName);
	}
	
}
