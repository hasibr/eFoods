package beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class Customer
{
	private String account;
	private String name;
	private String hash;
	
	
	public Customer() {
		
	}
	
	
	
	public Customer(String account, String name, String hash)
	{
		super();
		this.account = account;
		this.name = name;
		this.hash = hash;
	}



	@XmlAttribute(name = "account")
	public String getAccount()
	{
		return account;
	}
	public void setAccount(String account)
	{
		this.account = account;
	}
	
	@XmlElement
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	@XmlTransient
	public String getHash()
	{
		return hash;
	}
	public void setHash(String hash)
	{
		this.hash = hash;
	}
	
	@Override
	public String toString()
	{
		return "Customer [account=" + account + ", name=" + name + ", hash=" + hash + "]";
	}
	
	
	
	
	

}
