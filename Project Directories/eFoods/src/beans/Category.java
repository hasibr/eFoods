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
	
	/**
	 * 
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
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	
	

}
