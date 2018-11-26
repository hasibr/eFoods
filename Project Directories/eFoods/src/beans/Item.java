package beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * @author franciso
 * 
 * 
 * This is an item bean. stores every thing you need to know about an item.
 * Instantiation and price or quantity mutation automatically calculates it's 
 * total price. Less work for the developer to manually calculate the total
 * price everytime they change the quantity or even the price itself.
 *
 */
@XmlRootElement(name = "item")
@XmlType(propOrder = {"number","name","price","qty", "category", "totalPrice"})
@XmlAccessorType (XmlAccessType.PUBLIC_MEMBER)
public class Item{
	
	private String number, // product number / product id / ID. the Engine refers to this as "ItemID"
					name,
					price,
					qty, // number of the item
					category,
					totalPrice;
	
	
	public Item() {
		/**/
	}
	
	/**
	 * @param number
	 * @param name
	 * @param price
	 * @param category
	 * @param qty
	 */
	public Item(String number, String name, String price, String category, String qty)
	{
		super();
		this.number = number;
		this.name = name;
		this.price = String.format("$%.2f", Double.parseDouble(price.replace("$", "")));
		this.category = category;
		this.qty = qty;
		this.totalPrice = calcTotPrc(qty, price);
	}
	

	/**
	 * @param qty
	 * @param price
	 * @return
	 */
	private String calcTotPrc(String qty, String price) {
		
		int q = Integer.parseInt(qty);
		double p = Double.parseDouble(price.replace("$", ""));
		
		double tot = q * p;
		
		return String.format("$%.2f", tot);
		
	}
	
	/**
	 * Product number. unique to each product
	 * 
	 * @return
	 */
	@XmlAttribute
	public String getNumber()
	{
		return number;
	}




	/**
	 * @param number
	 */
	public void setNumber(String number)
	{
		this.number = number;
	}




	/**
	 * @return
	 */
	public String getName()
	{
		return name;
	}




	/**
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}




	/**
	 * @return
	 */
	public String getPrice()
	{
		return price.replace("$", "");
	}


	/**
	 * automaticallty updates the total price. programmer never has to manually
	 * update the total price.
	 * 
	 * @param price
	 */
	public void setPrice(String price)
	{
		this.price = price;
		
		double prc = Double.parseDouble(price.replace("$", ""));
		int q = Integer.parseInt(getQty());
		
		double total = prc * q;
		
		setTotalPrice(String.format("$%.2f", total));
	}



	/**
	 * @return
	 */
	@XmlElement(name = "quantity")
	public String getQty()
	{
		return qty;
	}



	/**
	 * automatically updates the total price. programmer never has to manually set
	 * the total price.
	 * 
	 * @param qty
	 */
	public void setQty(String qty)
	{
		this.qty = qty;
		
		double prc = Double.parseDouble(getPrice().replace("$", ""));
		int q = Integer.parseInt(qty);
		
		double total = prc * q;
		
		setTotalPrice(String.format("$%.2f", total));
		
		
	}
	
	@XmlTransient
	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	/**
	 * @return
	 */
	@XmlElement(name = "extended")
	public String getTotalPrice()
	{
		return totalPrice.replace("$", "");
	}


	/**
	 * @param totalPrice
	 */
	private void setTotalPrice(String totalPrice)
	{
		this.totalPrice = totalPrice;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Item [number=" + number + ", name=" + name + ", price=" + price + ", qty=" + qty + ", totalPrice="
				+ totalPrice + "]";
	}
}
