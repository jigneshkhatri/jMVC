package logs;

import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogManager {
    public static void appendToExceptionLogs(Exception ex) {
    	try {
    		boolean append = true;
    		 ResourceBundle rb = ResourceBundle.getBundle("config");
     		FileHandler handler = new FileHandler(rb.getString("logsLocation"), append);
    		Logger logger = Logger.getLogger("logs");
    		logger.addHandler(handler);
    		SimpleFormatter formatter = new SimpleFormatter();  
			handler.setFormatter(formatter);  
    		logger.warning(ex.toString());
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}
