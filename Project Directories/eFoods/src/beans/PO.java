package beans;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name = "order")
@XmlType(propOrder = {"id", "submitted", "customer", "items", "total", "shipping", "HST", "grandTotal"})
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class PO {
	
	String fileName;
	
	Customer customer;
	
	Items items;
	
	String total;
	String shipping;
	String HST;
	String grandTotal;
	
	String id;
	String submitted;
	
	
	public PO() {
		/**/
	}
	
	
	
	@XmlTransient
	public String getFileName()
	{
		return fileName;
	}
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public Customer getCustomer()
	{
		return customer;
	}
	public void setCustomer(Customer customer)
	{
		this.customer = customer;
	}
	
	@XmlElement
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
	
	@XmlAttribute(name = "id")
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	
	@XmlAttribute(name = "submitted")
	public String getSubmitted()
	{
		return submitted;
	}
	public void setSubmitted(String submitted)
	{
		this.submitted = submitted;
	}



	@Override
	public String toString()
	{
		return "PO [customer=" + customer + ", items=" + items + ", total=" + total + ", shipping=" + shipping
				+ ", HST=" + HST + ", grandTotal=" + grandTotal + ", id=" + id + ", submitted=" + submitted + "]";
	}
	
	
	
	

}
