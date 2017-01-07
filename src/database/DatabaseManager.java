/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;
import logs.LogManager;

public class DatabaseManager {

    public Connection conn = null;

    public DatabaseManager() {
       
    }

    protected void initConnection() {
        try {
            ResourceBundle rb = ResourceBundle.getBundle("config");
            Class.forName(rb.getString("dbDriverClassName"));
            conn = DriverManager.getConnection(rb.getString("dbUrl") + ":" + rb.getString("dbPort") + "/" + rb.getString("dbName"), rb.getString("dbUsername"), rb.getString("dbPassword"));

        } catch (Exception ex) {
            LogManager.appendToExceptionLogs(ex);
        }
    }

    protected void closeConnection() {
        try {
        	if(!conn.isClosed()) conn.close();
        } catch (Exception ex) {
            LogManager.appendToExceptionLogs(ex);
        }
    }
}
