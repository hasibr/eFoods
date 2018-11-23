package beans;

import java.util.ArrayList;
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
public class ShoppingCart
{
	HashMap<String,Item> items;
	
	String subTotal;
	String tax;
	String shipping;
	String total;
	
	public ShoppingCart() {
		/**/
		this.items = new HashMap<String,Item>();
		this.subTotal = "0.00";
		this.tax = "0.00";
		this.shipping = "0.00";
		this.total = "0.00";
	}

	public ShoppingCart(HashMap<String,Item> items, String subTotal)
	{
		super();
		this.items = items;
		this.subTotal = subTotal;
	}

	public HashMap<String, Item> getItems()
	{
		return items;
	}
	
	
	public void setItems(HashMap<String, Item> items)
	{
		this.items = items;
		
		double tot = 0.00;
		for(Item item: items.values()) {
			
			// sum up the total prices of all the items.
			tot += Double.parseDouble(item.getTotalPrice().replace("$", ""));
			
		}
		
		setSubTotal(String.format("%.2f", tot));
		setTax(tot + "");
		setShipping(tot + "");
		setTotal();
	}

	public String getSubTotal()
	{
		return subTotal;
	}

	private void setSubTotal(String subTotal)
	{
		this.subTotal = subTotal;
	}
	
	public String getTax()
	{
		return tax;
	}
	
	/*
	 * sets the tax based on what the sub total is
	 */
	private void setTax(String subTotal)
	{
		double hst = Double.parseDouble(subTotal) * 0.13;
		this.tax = String.format("%.2f", hst);
	}

	public String getShipping()
	{
		return shipping;
	}

	private void setShipping(String subTotal)
	{
		double sTot = Double.parseDouble(subTotal);
		
		if(sTot <= 0.00 || sTot >= 100.0) {
			this.shipping = "0.00";
		}
		else {
			this.shipping = "5.00";
		}
		
	}

	public String getTotal()
	{
		return total;
	}

	private void setTotal()
	{
		double a = Double.parseDouble(getSubTotal());
		double b = Double.parseDouble(getShipping());
		double c = Double.parseDouble(getTax());
		
		double d = a + b + c;
		
		
		this.total = String.format("%.2f", d);
	}
	
	
	public Items getItemsObject(){
		
		ArrayList<Item> l = new ArrayList<>();
		
		for(String s : this.items.keySet()) {
			l.add(this.items.get(s));
		}
		
		Items i = new Items();
		i.setItm(l);
		
		return i;
	}
	
}
