package beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 * 
 * @author franciso
 * 
 * Represents a client. This is used for XML marshaling as well as a way of determining
 * if a client has logged in. and instance of this bean is created after a successful login.
 * when the user logs out this object is destroyed
 *
 */
@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class Customer
{
	private String account;
	private String name;
	private String hash;
	
	
	public Customer() {
		
	}
	
	
	
	/**
	 * @param account
	 * @param name
	 * @param hash
	 */
	public Customer(String account, String name, String hash)
	{
		super();
		this.account = account;
		this.name = name;
		this.hash = hash;
	}



	/**
	 * the clients username
	 * 
	 * @return
	 */
	@XmlAttribute(name = "account")
	public String getAccount()
	{
		return account;
	}
	/**
	 * @param account
	 */
	public void setAccount(String account)
	{
		this.account = account;
	}
	
	/**
	 * The clients actual name
	 * 
	 * @return
	 */
	@XmlElement
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
	 * A hash of the clients credentials. not used in this project, but is here if we 
	 * need it.
	 * 
	 * @return
	 */
	@XmlTransient
	public String getHash()
	{
		return hash;
	}
	/**
	 * @param hash
	 */
	public void setHash(String hash)
	{
		this.hash = hash;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Customer [account=" + account + ", name=" + name + ", hash=" + hash + "]";
	}
	
	
	
	
	

}
