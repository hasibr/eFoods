package beans;

public class PersonBean
{
	String username;
	String name;
	String hash;
	
	
	
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
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
		return "PersonBean [username=" + username + ", name=" + name + ", hash=" + hash + "]";
	}
	
	
	
	
	

}
