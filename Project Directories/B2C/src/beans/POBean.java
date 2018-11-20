package beans;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "order")
public class POBean {
	
	HashMap<String,ItemBean> items;
	
	String total;
	String shipping;
	String HST;
	String grandTotal;
	
	
	
	public HashMap<String, ItemBean> getItems() {
		return items;
	}
	public void setItems(HashMap<String, ItemBean> items) {
		this.items = items;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getShipping() {
		return shipping;
	}
	public void setShipping(String shipping) {
		this.shipping = shipping;
	}
	public String getHST() {
		return HST;
	}
	public void setHST(String hST) {
		HST = hST;
	}
	public String getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(String grandTotal) {
		this.grandTotal = grandTotal;
	}
	
	
	

}
