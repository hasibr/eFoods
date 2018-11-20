package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import beans.CartBean;
import beans.CategoryBean;
import beans.ItemBean;

public class Engine {
	
	private static Engine instance = null;
	
	private static CategoryDAO catDAO = null;
	private static ItemDAO itemDAO = null;
	
	private Engine() throws Exception {
		
		catDAO = new CategoryDAO();
		itemDAO = new ItemDAO();
		
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception. contains the message(s) of error(s) that are encountered
	 * during runtime.
	 */
	public synchronized static Engine getInstance() throws Exception {
		
		if (instance == null) {
			
			instance =  new Engine();
		}
		
		return instance;
	}
	
	
	/**
	 * 
	 * @return a list containing category beans. These beans contain the names, id's and
	 * descriptions of all the different categories offered by Foods R Us.
	 */
	public List<CategoryBean> doCategory(){
		
		return catDAO.retrieve();
	}
	
	
	/**
	 * Retrieves a list if food items from the database depending on the parameters specified
	 * @param name name of the food item
	 * @param sortBy category to sort by
	 * @param catID catalog ID of the food item
	 * @return list containing all the food items in the database that meet the requirements 
	 * @throws Exception
	 */
	public List<ItemBean> doBrowse(String name, String sortBy, String catID) throws Exception{
		
		return itemDAO.retrieve(name, sortBy, catID);
	}
	
	
	
	public CartBean doCart(ItemBean item, CartBean cart, String add,
			String update, String cancel, Map<String, String[]> parameters) {
		
		
		if(cancel != null && cancel.equalsIgnoreCase("true")) {
			return emptyCart(cart);
//			System.out.println("cancel called");
		}
		if(add != null && add.equalsIgnoreCase("true")) {
//			System.out.println("add called");
			return addToCart(cart, item);
		}
		
		if(update != null && update.equalsIgnoreCase("true")) {
//			System.out.println("Update called");
			return updateCart(cart, parameters);
		}
		
		
//		System.out.println("nothing called");	
		return cart;
		
	}
	
	
	public CartBean doCheckout(CartBean cart) {
		
		return cart;
	}
	
	
	public void doConfirm() {
		
		
	}
	
	
//------------------------- helper methods --------------------------------------------------
	
	/**
	 * 
	 * Takes the cart of the customer and adds an item to it.
	 * 
	 * @param cart the clients cart
	 * @param item item they are adding
	 * @return cart with the added item(s)
	 */
	private CartBean addToCart(CartBean cart, ItemBean item) {
		
		String itemID = item.getNumber(); //"itemID" (unique product number) of the specific item.
		int orderQty = Integer.parseInt(item.getQty()); // quantity the client wants to order
		
		/**
		 * this is a hash map that represents all the items in the shopping cart. the key is the unique
		 * "itemID" assigned to each item. the value is a bean that represents that item.
		 * the bean has a quantity attribute that changes depending on of the client adds or removes that
		 * same item. the total price attribute changes automatically.
		 */
		HashMap<String,ItemBean> itemsInCart = cart.getItems();
		
		
		/*
		 * if this item(s) is not currently in the cart, add it (the item(s))
		 * to the cart and return the cart
		 */
		if(itemsInCart.get(itemID) == null) {
			
			// maps the "itemID" to the item bean that the client added
			itemsInCart.put(itemID, item);
			
			// puts that hash map in the cart.
			cart.setItems(itemsInCart);
			return cart;
		}
		/**
		 * an item with this "itemID" is already in the shopping cart so we must increase
		 * the quantity as well as the total price of the cart
		 */
		else {
			
			// get the instance of the itme that is already in the cart
			ItemBean b = itemsInCart.get(itemID);
			
			// adds the qty the client wants with the qty already in the cart
			int newQty = orderQty + Integer.parseInt(b.getQty());
			
			
			//set the new quantity of the item
			b.setQty(newQty+"");
			
			// update the hash map with the updated item bean
			itemsInCart.put(itemID, b);
			
			// put the updated hash map back in the cart
			cart.setItems(itemsInCart);
			return cart;
	
		}
			
	}
	
	
	/**
	 * 
	 * Takes the cart and updates the quantities of the items inside it.
	 * 
	 * @param cart
	 * @param parameters
	 * @return
	 */
	private CartBean updateCart(CartBean cart, Map<String, String[]> parameters) {
		
		
		if(cart.getItems().isEmpty()) {
			return cart;
		}
		
		Set<String> keys = parameters.keySet();
		List<String> delete;
		
		/*
		 * for the case that none of the delete check boxes are checked
		 *///----------------------------------------------------------
		if(parameters.get("delete") == null) {
			delete = new ArrayList<String>();
		}
		else {
			delete = Arrays.asList(parameters.get("delete"));
		}
		//-------------------------------------------------------------
		
		HashMap<String, ItemBean> items = cart.getItems();
		
		
		/*
		 *  for each product number of the items in the cart, update its quantity.
		 *  
		 *  if the delete box was checked, or the client entered "0" for the quantity,
		 *  remove that item from the cart. else change the quantity of that item.
		 */
		for(String key : keys) {
			if(!key.equalsIgnoreCase("update") && !key.equalsIgnoreCase("delete")) {
				
				// update the quantity of the item with this "key" (product id) in the cart
				String qty = parameters.get(key)[0];
				
				if(qty.equals("0") || delete.contains(key)) {
					items.remove(key);
				}
				else {
					items.get(key).setQty(qty);
				}
				
			}
		}
		
		cart.setItems(items);
		
		
		return cart;
	}
	
	/**
	 * given the shopping cart, remove all the items inside it.
	 * 
	 * @param cart
	 * @return
	 */
	private CartBean emptyCart(CartBean cart) {
		
		cart.setItems(new HashMap<String,ItemBean>());
		
		return cart;
	}
	
//------------------------- end helper methods ---------------------------------------
}
