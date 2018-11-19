package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author francis okoyo
 * 
 * when instantiated, gathers all the categories from the data base and returns them
 * in the form of a list of category beans.
 *
 */
public class CategoryDAO{
	
	private static final String DB_URL = "jdbc:derby://localhost:64413/EECS;user=student;password=secret";
//	public static final String DB_URL = "jdbc:derby://red.eecs.yorku.ca:64413/EECS;user=student;password=secret";

	
	private List<CategoryBEAN> beans;
	
	public CategoryDAO() throws Exception {
		
		beans = new ArrayList<CategoryBEAN>();
		
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
			Connection con = DriverManager.getConnection(DB_URL);
			Statement s = con.createStatement();
			s.executeUpdate("set schema roumani");
			
			String query = "SELECT * FROM CATEGORY";
			
			
			ResultSet r = s.executeQuery(query);
			
			CategoryBEAN bean;
			
			while(r.next()) {
				
				bean = new CategoryBEAN(r.getString("NAME"), r.getString("DESCRIPTION"), r.getString("ID"));
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
			throw new Exception("Database encounterd an error. Please Try again later.");
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new Exception("We're having difficulties right now. Please try again later");
		}
		
	}
	
	public List<CategoryBEAN> retrieve() {
		
		return this.beans;
	}

}
