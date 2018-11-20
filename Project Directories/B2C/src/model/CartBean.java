package model;

import java.util.HashMap;

/**
 * 
 * @author francis okoyo
 * 
 * Represents a shopping cart. Stores collection of all the food items the client has
 * added to the shopping cart. The collection is a hash map. the key is the item's product
 * number / ID. the value is and item bean representing an item with that product number.
 * 
 * the second attribute is a string representing the sum of all the prices of the items in this
 * cart.
 * 
 * 
 * 
 * NOTES: later on in this applications development, i may implement a method for this bean to calculate
 * it's total price automatically.
 *
 */
public class CartBean
{
	HashMap<String,ItemBean> items;
	
	String subTotal;

	public CartBean(HashMap<String,ItemBean> items, String subTotal)
	{
		super();
		this.items = items;
		this.subTotal = subTotal;
	}

	public HashMap<String, ItemBean> getItems()
	{
		return items;
	}
	
	//MAY IMPLEMET A FEATURE IN THIS METHOD THAT LOOPS THROUGH THE ITEMS INSIDE
	//IT AND UPDATE THE SUBTOTAL AUTOMATICALLY.
	public void setItems(HashMap<String, ItemBean> items)
	{
		this.items = items;
		
		double total = 0.00;
		for(ItemBean item: items.values()) {
			
			// sum up the total prices of all the items.
			total += Double.parseDouble(item.getTotalPrice().replace("$", ""));
			
		}
		
		setSubTotal(String.format("$%.2f", total));
	}

	public String getSubTotal()
	{
		return subTotal;
	}

	private void setSubTotal(String subTotal)
	{
		this.subTotal = subTotal;
	}
	
}
