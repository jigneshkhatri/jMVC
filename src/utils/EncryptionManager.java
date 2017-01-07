package utils;

import java.util.Base64;

import logs.LogManager;

public class EncryptionManager {
    
    public static String ENCRYPTIONKEY="AIzaSyCSdLr3oYxc";

     public static String encryptData(String data) {
        String result = "";
        try {
            result = new String(Base64.getEncoder().encode((data.getBytes())));
        } catch (Exception ex) {
            LogManager.appendToExceptionLogs(ex);
        }
        return result;
    }
    public static String decryptData(String data) {
        String result = "";
        try {
            result = new String((Base64.getDecoder().decode(data)));
        } catch (Exception ex) {
            LogManager.appendToExceptionLogs(ex);
        }
        return result;
    }
}
