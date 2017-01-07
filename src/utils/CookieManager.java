package utils;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.EncryptionManager;

public class CookieManager {
	/*public static int adminCookieCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
		  String cookieName = cookies[i].getName();
		  String cookieValue = cookies[i].getValue();
		  if(cookieName.equals("ucAdmin")) {
			  Uc_admin ucAdmin = new Uc_admin();
			  Model model = new Model();
			  List<Uc_admin> list = new ArrayList<Uc_admin>();
			  String whereClause = "a_username='" + cookieValue + "'";
			  list = (List<Uc_admin>)(Object) model.selectData("model.Uc_admin", whereClause);
			  ucAdmin = list.get(0);
			  if(ucAdmin.getA_active() == 1) {
				  HttpSession session = request.getSession();
				  Hashtable sessionAttributes = new Hashtable();
				  sessionAttributes.put("uusername", ucAdmin.getA_username());
				  session.setAttribute("admin", sessionAttributes);
				  
				  response.sendRedirect(request.getContextPath() + "/admin/home");
			  } else {
				  response.sendRedirect(request.getContextPath() + "/AdminUserAccounts?action=logout");
			  }
		  } 
		}
	}*/
	
	public static String cookieExists(HttpServletRequest request, String cookieName) throws ServletException, IOException {
		int flag = 0;
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		for (int i = 0; i < cookies.length; i++) {
		  String matchCookieName = cookies[i].getName();
		  if(matchCookieName.equals(cookieName)) {
			  flag = 1;
			  cookie = cookies[i];
			  break;
		  } 
		}
		if(flag == 0) return null;
		else {
			String cookieValue = EncryptionManager.decryptData(cookie.getValue());
			return cookieValue;
		}
	}
	
	public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue, int cookieAge) throws ServletException, IOException {
		String encryptedCookieValue = EncryptionManager.encryptData(cookieValue);
		Cookie cookie = new Cookie(cookieName, encryptedCookieValue);
		cookie.setMaxAge(cookieAge);
		response.addCookie(cookie);
	}
	
	public static void unsetCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			String matchCookieName = cookies[i].getName();
			if(matchCookieName.equals(cookieName)) {
				cookies[i].setMaxAge(0);
				response.addCookie(cookies[i]);
				break;
			}
		}
	}
	
	public static void renewCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, int cookieAge) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			String matchCookieName = cookies[i].getName();
			if(matchCookieName.equals(cookieName)) {
				cookies[i].setMaxAge(cookieAge);
				response.addCookie(cookies[i]);
				break;
			}
		}
	}
}
