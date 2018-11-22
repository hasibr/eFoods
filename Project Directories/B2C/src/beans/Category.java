package beans;


/**
 * 
 * @author francis okoyo
 *
 *
 *
 * represents a category. nothing special here.
 */
public class Category{
	
	private String name;
	private String description;
	private String id;
	
	public Category() {
		/**/
	}
	
	public Category(String name, String desc, String id) {
		
		this.name = name;
		this.description = desc;
		this.id = id;
		
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	
	

}
