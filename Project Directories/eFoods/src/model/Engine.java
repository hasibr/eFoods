package model;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import beans.ShoppingCart;
import beans.Category;
import beans.Item;
import beans.PO;
import beans.Customer;

public class Engine {
	
	private static Engine instance = null;
	
	private static CategoryDAO catDAO = null;
	private static ItemDAO itemDAO = null;
	
	private Engine(){
		
		catDAO = new CategoryDAO();
		itemDAO = new ItemDAO();
		
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception. contains the message(s) of error(s) that are encountered
	 * during runtime.
	 */
	public synchronized static Engine getInstance(){
		
		if (instance == null) {
			
			instance =  new Engine();
		}
		
		return instance;
	}
	
	
	/**
	 * 
	 * @return a list containing category beans. These beans contain the names, id's and
	 * descriptions of all the different categories offered by Foods R Us.
	 * @throws Exception 
	 */
	public List<Category> doCategory() throws Exception{
		
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
	public List<Item> doBrowse(String name, String sortBy, String catID) throws Exception{
		
		return itemDAO.retrieve(name, sortBy, catID);
	}
	
	
	/**
	 * Performs 3 different actions depending on the parameters passed to it. if the client
	 * cancels their order, this method calls a helper function that deletes all the items in
	 * the shopping cart.
	 * 
	 * if the customer adds something to the cart, calls a helper function that adds said item
	 * to the shopping cart.
	 * 
	 * if the customer chooses to update their cart, calls a helper function that may add more,
	 * remove some or delete items completely from the shopping cart.
	 * 
	 * All 3 of these functions return an instance of the shopping cart after it has been mutated.
	 * 
	 * @param item
	 * @param cart
	 * @param add
	 * @param update
	 * @param cancel
	 * @param parameters
	 * @return
	 */
	public ShoppingCart doCart(Item item, ShoppingCart cart, String add,
			String update, String cancel, Map<String, String[]> parameters) {
		
		
		if(cancel != null && cancel.equalsIgnoreCase("true"))
			return emptyCart(cart);
		
		
		if(add != null && add.equalsIgnoreCase("true"))
			return addToCart(cart, item);
		
		
		if(update != null && update.equalsIgnoreCase("true")) 
			return updateCart(cart, parameters);
		
		
	
		return cart;
		
	}
	
	/**
	 * On Checkout, checks if the chopping cart is empty.
	 * @param cart
	 * @return
	 */
	public boolean doCheckout(ShoppingCart cart) {
		
		return cart.getItems().isEmpty();
	}
	
	/**
	 * this is called when the user has authenticated and has confirmed their order.
	 * creates a PO object and marshals its contents to an XML file stores on disk
	 * with the name po<x>_01.xml where x is the clients account name and 01 is a number
	 * that signifies the number of POs that client has created.
	 * 
	 * @param cart
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	public ShoppingCart doConfirm(ShoppingCart cart, Customer customer) throws Exception {
		
		PODAO poDao = new PODAO();
		
		/*
		 * set PO attributes
		 */
		PO po = new PO();
		
		po.setItems(cart.getItemsObject());
		po.setTotal(cart.getSubTotal());
		po.setShipping(cart.getShipping());
		po.setHST(cart.getTax());
		po.setGrandTotal(cart.getTotal());
		
		
		/*
		 * set customer attributes
		 */
		Customer cus = new Customer();
		
		cus.setAccount(customer.getAccount());
		cus.setName(customer.getName());
		cus.setHash(customer.getHash());
		
		/*
		 * put the customer bean inside the PO bean
		 */
		po.setCustomer(cus);
		
		/*
		 * create a date object and format it
		 */
		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String d = df.format(date);
		
		/*
		 * sets the id# of the PO. this is the total number of PO's created so far.
		 * (sum of all the independent PO's created by each client)
		 */
		po.setId((1 + poDao.rootFileCount())+"");
		po.setSubmitted(d);
		
		/*
		 * sets the filename for each PO. (po<x>_01.xml)
		 * the client account name is <x> and the number is
		 * gotten by checking the storage directory for files
		 * whose name contain that account name.
		 */
		String filePrefix = cus.getAccount();
		int fileCount = poDao.filesWithName("po"+filePrefix+"_") + 1;
		
		//set the filename and store it on disk.
		String filename = String.format("po%s_%02d.xml", filePrefix, fileCount);
		poDao.storeFile(filename, po);
		
		// empty the cart
		return emptyCart(cart);
	}
	
	
	
	/**
	 * given an account, returns a list of PO objects associated with that account
	 * 
	 * @param accountName
	 * @return List<PO>
	 * @throws Exception
	 */
	public List<PO> doAccount(String accountName) throws Exception{
		PODAO p = new PODAO();
		
		return p.getPOs(accountName);
	}
	
	
	/**
	 * Used by the account servlet to find a PO file to display.
	 * this PO is in s list of PO's specific to the user. it is
	 * impossible for a client to access another clients POs.
	 * 
	 * @param pos
	 * @param fileName
	 * @return
	 */
	public PO findPO(List<PO> pos, String fileName) {
		
		for(PO p : pos) {
			if(p.getFileName().equals(fileName)) {
				return p;
			}
		}
		
		return null;
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
	private ShoppingCart addToCart(ShoppingCart cart, Item item) {
		
		String itemID = item.getNumber(); //"itemID" (unique product number) of the specific item.
		int orderQty = Integer.parseInt(item.getQty()); // quantity the client wants to order
		
		/**
		 * this is a hash map that represents all the items in the shopping cart. the key is the unique
		 * "itemID" assigned to each item. the value is a bean that represents that item.
		 * the bean has a quantity attribute that changes depending on of the client adds or removes that
		 * same item. the total price attribute changes automatically.
		 */
		HashMap<String,Item> itemsInCart = cart.getItems();
		
		
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
			Item b = itemsInCart.get(itemID);
			
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
	private ShoppingCart updateCart(ShoppingCart cart, Map<String, String[]> parameters) {
		
		
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
		
		HashMap<String, Item> items = cart.getItems();
		
		
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
	private ShoppingCart emptyCart(ShoppingCart cart) {
		
		cart.setItems(new HashMap<String,Item>());
		
		return cart;
	}
	
//------------------------- end helper methods ---------------------------------------
}
