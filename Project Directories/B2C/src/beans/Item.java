package beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * @author francis okoyo
 * 
 * 
 * This is an item bean. stores every thing you need to know about an item. on
 * Instantiation, automatically calculates it's total price.
 *
 */
@XmlRootElement(name = "item")
@XmlType(propOrder = {"number","name","price","qty","totalPrice"})
@XmlAccessorType (XmlAccessType.PUBLIC_MEMBER)
public class Item{
	
	private String number, // product number / product id / ID. the Engine refers to this as "ItemID"
					name,
					price,
					qty, // number of the item
					totalPrice;
					//catid;  // catalog id
	
	
	public Item() {
		/**/
	}
	
	public Item(String number, String name, String price, String qty)
	{
		super();
		this.number = number;
		this.name = name;
		this.price = String.format("$%.2f", Double.parseDouble(price.replace("$", "")));
		this.qty = qty;
		this.totalPrice = calcTotPrc(qty, price);
//		this.catid = "N/A";
	}
	
	
	

//	public Item(String number, String name, String price, String qty, String catid)
//{
//	super();
//	this.number = number;
//	this.name = name;
//	this.price = price;
//	this.qty = qty;
//	this.totalPrice = calcTotPrc(qty, price);
//	this.catid = catid;
//}


	private String calcTotPrc(String qty, String price) {
		
		int q = Integer.parseInt(qty);
		double p = Double.parseDouble(price.replace("$", ""));
		
		double tot = q * p;
		
		return String.format("$%.2f", tot);
		
	}
	
	@XmlAttribute
	public String getNumber()
	{
		return number;
	}




	public void setNumber(String number)
	{
		this.number = number;
	}




	public String getName()
	{
		return name;
	}




	public void setName(String name)
	{
		this.name = name;
	}




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



	@XmlElement(name = "extended")
	public String getTotalPrice()
	{
		return totalPrice.replace("$", "");
	}


	/**
	 * Assumes you know what the total price is. please use this responsibly
	 * @param totalPrice
	 */
	private void setTotalPrice(String totalPrice)
	{
		this.totalPrice = totalPrice;
	}


//
//
//	public String getCatid()
//	{
//		return catid;
//	}
//
//
//
//
//	public void setCatid(String catid)
//	{
//		this.catid = catid;
//	}
//
//


	
	
	
}
