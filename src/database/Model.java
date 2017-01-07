package database;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import logs.LogManager;

public class Model {
	private DatabaseManager dbManager;
	private Statement statement;
	private StringBuilder query;
	private Method m;
	private Class c_values;
	private String tableName;
	private String defaultValue;
	private boolean isTransaction;
	
	public Model() {
		ResourceBundle rb = ResourceBundle.getBundle("config");
		defaultValue = rb.getString("defaultValue");
		dbManager = new DatabaseManager();
		isTransaction = false;
	}
	
	public void startTransaction() {
		try {
			dbManager.initConnection();
			dbManager.conn.setAutoCommit(false);
			isTransaction = true;
		} catch(Exception ex) {
			LogManager.appendToExceptionLogs(ex);
		}
	}
	
	public void endTransaction() {
		try {
			dbManager.conn.commit();
			dbManager.closeConnection();
			isTransaction = false;
		} catch(Exception ex) {
			LogManager.appendToExceptionLogs(ex);
		}
	}
	
	public boolean insertData(Object values) {
		try {
			//dbManager = new DatabaseManager();
			c_values = values.getClass();
			tableName = c_values.getName().toString().toLowerCase().substring(9);
			Field flds[] = c_values.getDeclaredFields();
			query = new StringBuilder();
			query.append("insert into " + tableName + " set ");
			for(int i = 0 ; i < flds.length ; i++) {
				m = c_values.getDeclaredMethod("get" + flds[i].getName().substring(0,1).toUpperCase() + flds[i].getName().substring(1).toLowerCase(), null);
				if(m.invoke(values).toString().equals(defaultValue)) continue;
				else {
					if(i >= (flds.length - 1)) {
						query.append(flds[i].getName().toString() + "='" + m.invoke(values) + "'");
					} else {
						query.append(flds[i].getName().toString() + "='" + m.invoke(values) + "',");
					}
				}
			}
			if(isTransaction) {
				statement = dbManager.conn.createStatement();
				statement.executeUpdate(query.toString());
				System.out.println("Model Query: " + query);
			} else {
				dbManager.initConnection();
				statement = dbManager.conn.createStatement();
				statement.executeUpdate(query.toString());
				System.out.println("Model Query: " + query);
			}
			return true;
		} catch(Exception ex) {
			LogManager.appendToExceptionLogs(ex);
			return false;
		} finally {
			if(!isTransaction) {
				dbManager.closeConnection();
			}
		}
	}
	
	public int getLastInsertedID() {
		int lastID = 0;
		try {
			//dbManager = new DatabaseManager();
			dbManager.initConnection();
			query = new StringBuilder("select LAST_INSERT_ID()");
			statement = dbManager.conn.createStatement();
			ResultSet rs = statement.executeQuery(query.toString());
			rs.next();
			lastID = rs.getInt(1);
			return lastID;
		} catch(Exception ex) {
			LogManager.appendToExceptionLogs(ex);
			return lastID;
		} finally {
			if(!isTransaction) {
				dbManager.closeConnection();
			}
		}
	}
	
	public boolean updateData(Object values, String whereClause) {
		try {
			//dbManager = new DatabaseManager();
			c_values = values.getClass();
			tableName = c_values.getName().toString().toLowerCase().substring(9);
			Field flds[] = c_values.getDeclaredFields();
			query = new StringBuilder();
			query.append("update " + tableName + " set ");
			for(int i = 0 ; i < flds.length ; i++) {
				m = c_values.getDeclaredMethod("get" + flds[i].getName().substring(0,1).toUpperCase() + flds[i].getName().substring(1).toLowerCase(), null);
				if(m.invoke(values).toString().equals(defaultValue)) continue;
				else {
					if(i >= (flds.length - 1)) {
						query.append(flds[i].getName().toString() + "='" + m.invoke(values) + "'");
					} else {
						query.append(flds[i].getName().toString() + "='" + m.invoke(values) + "',");
					}
				}
			}
			query.append(" where " + whereClause);
			if(isTransaction) {
				statement = dbManager.conn.createStatement();
				statement.executeUpdate(query.toString());
				System.out.println("Model Query: " + query);
			} else {
				dbManager.initConnection();
				statement = dbManager.conn.createStatement();
				statement.executeUpdate(query.toString());
				System.out.println("Model Query: " + query);
			}
			return true;
		} catch(Exception ex) {
			LogManager.appendToExceptionLogs(ex);
			return false;
		} finally {
			if(!isTransaction) {
				dbManager.closeConnection();
			}
		}
	}
	
	public boolean deleteData(String tableName, String whereClause) {
		try {
			//dbManager = new DatabaseManager();
			query = new StringBuilder();
			query.append("delete from " + tableName + " where " + whereClause);
			if(isTransaction) {
				statement = dbManager.conn.createStatement();
				statement.executeUpdate(query.toString());
				System.out.println("Model Query: " + query);
			} else {
				dbManager.initConnection();
				statement = dbManager.conn.createStatement();
				statement.executeUpdate(query.toString());
				System.out.println("Model Query: " + query);
			}
			return true;
		} catch(Exception ex) {
			LogManager.appendToExceptionLogs(ex);
			return false;
		} finally {
			if(!isTransaction) {
				dbManager.closeConnection();
			}
		}
	}
	
	public List<Object> selectData(String classPath, String whereClause) {
		try {
			//dbManager = new DatabaseManager();
			dbManager.initConnection();
			String tableName = classPath.substring((classPath.indexOf('.') + 1)).toLowerCase();
			c_values = Class.forName(classPath);
			Field flds[] = c_values.getDeclaredFields();
			query = new StringBuilder();
			query.append("select * from " + tableName + " where " + whereClause);
			statement = dbManager.conn.createStatement();
			ResultSet rs = statement.executeQuery(query.toString());
			System.out.println("Model Query: " + query);
			List<Object> list = new ArrayList<Object>();
			while(rs.next()) {
				Object obj = c_values.newInstance();
				for(int i = 0 ; i < flds.length ; i++) {
					m = c_values.getDeclaredMethod("set" + flds[i].getName().substring(0,1).toUpperCase() + flds[i].getName().substring(1).toLowerCase(), flds[i].getType());
					m.invoke(obj,  rs.getObject(flds[i].getName().toString()));
				}
				list.add(obj);
			}
			return list;
		} catch(Exception ex) {
			LogManager.appendToExceptionLogs(ex);
			ex.printStackTrace();
			return null;
		} finally {
			if(!isTransaction) {
				dbManager.closeConnection();
			}
		}
	}
}
