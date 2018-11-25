package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.Category;

/**
 * 
 * @author franciso
 * 
 * Gets the categories from the local database
 *
 */
public class CategoryDAO{
	
	private static final String DB_URL = "jdbc:derby://localhost:64413/EECS;user=student;password=secret";
//	public static final String DB_URL = "jdbc:derby://red.eecs.yorku.ca:64413/EECS;user=student;password=secret";

	
	public CategoryDAO(){/**/}
	
	/**
	 * 
	 * connect to the local database and extracts all the necessary information from the CATALOG table
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Category> retrieve() throws Exception {
		
		List<Category> beans = new ArrayList<Category>();
		
		try {
			
			/*
			 * connect to the local database and gather everything from the CATEGORY table
			 */
			Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
			Connection con = DriverManager.getConnection(DB_URL);
			Statement s = con.createStatement();
			s.executeUpdate("set schema roumani");
			
			String query = "SELECT * FROM CATEGORY";
			
			
			ResultSet r = s.executeQuery(query);
			
			Category bean;
			
			while(r.next()) {
				
				bean = new Category(r.getString("NAME"), r.getString("DESCRIPTION"), r.getString("ID"));
				beans.add(bean);
				
			}
			r.close(); s.close(); con.close();
							
		}
		catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			throw new Exception("Could not initialize derby. Please try again later");
		}
		catch(SQLException sqle) {
			sqle.printStackTrace();
			throw new Exception("An error occured while trying to access our database. "
					+ "The server may be down."
					+ ". Please Try again later.");
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new Exception("To be honest, we dont know what happend. Were working on it."
					+ " Please try again later. Sorry for the inconvenience");
		}
		
		return beans;
	}

}
