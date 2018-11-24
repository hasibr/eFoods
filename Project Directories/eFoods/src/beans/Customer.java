package beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 * @author franciso
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
