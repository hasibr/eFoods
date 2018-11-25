package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.Item;

public class ItemDAO{
	
	private static final String DB_URL = "jdbc:derby://localhost:64413/EECS;user=student;password=secret";
//	public static final String DB_URL = "jdbc:derby://red.eecs.yorku.ca:64413/EECS;user=student;password=secret";

	
	public ItemDAO(){ }
	
	
	/**
	 * Searches the database for food items based on the parameters provided by the client.
	 * 
	 * @param catID
	 * @return returns a list of all the food items in the database
	 * based on the category given
	 * @throws Exception 
	 */
	public List<Item> retrieve(String foodName, String sortBy, String catID) throws Exception{
		
		try {
			List<Item> beans = new ArrayList<Item>();
			
			/*
			 * connect to the database
			 */
			Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
			Connection con = DriverManager.getConnection(DB_URL);
			Statement s = con.createStatement();
			s.executeUpdate("set schema roumani");
			
			
			String query;
			foodName = foodName.toLowerCase();
			
			/*
			 * (IF) - if the client specified a food name, retrieve the food items matching that food name
			 * from the table sorted in the order specified my the sortBy parameter.
			 * 
			 * (ELSE) - if the didnt specify a food name, get ALL the food items sorted by sortBy
			 */
			if(!foodName.trim().isEmpty()) {
				if (catID.equalsIgnoreCase("none")) {
					query = "SELECT * FROM ITEM WHERE lower(NAME) LIKE '%"+foodName+"%' ORDER BY "+ sortBy;
				}
				else {
					query = "SELECT * FROM ITEM WHERE lower(NAME) LIKE '%"+foodName+"%' and CATID = " + catID + " ORDER BY "+ sortBy;
				}
			}
			else {
				if (catID.equalsIgnoreCase("none")) {
					query = "SELECT * FROM ITEM ORDER BY "+ sortBy;
				}
				else {
					query = "SELECT * FROM ITEM WHERE CATID = " + catID + " ORDER BY "+ sortBy;
				}
			}
			
			
			
			
			ResultSet r = s.executeQuery(query);
			
			Item bean;
			
			/*
			 * loop through the result set and extract all the necessary information
			 */
			while(r.next()) {
				
				String number = r.getString("NUMBER"),
						name = r.getString("NAME"),
						price = String.format("%.2f", Double.parseDouble(r.getString("PRICE"))),
						qty = "1";
				
				bean = new Item(number, name, price, qty);
				beans.add(bean);
				
			}
			r.close(); s.close(); con.close();
			
			/////////
			return beans;
							
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
		
	}

}
