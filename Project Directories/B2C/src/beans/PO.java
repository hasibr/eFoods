package beans;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name = "order")
@XmlType(propOrder = {"items", "total", "shipping", "HST", "grandTotal"})
@XmlAccessorType(XmlAccessType.FIELD)
public class PO {
	
	@XmlElement
	Items items;
	
	String total;
	String shipping;
	String HST;
	String grandTotal;
	
//	String id;
//	String submitted;
	
	
	
	public Items getItems()
	{
		return items;
	}
	public void setItems(Items items)
	{
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
//	public String getId()
//	{
//		return id;
//	}
//	public void setId(String id)
//	{
//		this.id = id;
//	}
//	public String getSubmitted()
//	{
//		return submitted;
//	}
//	public void setSubmitted(String submitted)
//	{
//		this.submitted = submitted;
//	}
	
	
	
	

}
