package beans;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author franciso
 * 
 * Not to be confused with the "Item" class, this class "Items" is a bean that contains
 * a list of "Item" beans. The sole purpose of this bean is to make XML bean marshaling
 * easier.
 *
 */
@XmlRootElement(name = "items")
@XmlAccessorType (XmlAccessType.FIELD)
public class Items
{
	@XmlElement(name = "item")
	private List<Item> itm;
	
	public Items() {
		/**/
	}

	public List<Item> getItm()
	{
		return itm;
	}

	public void setItm(List<Item> itm)
	{
		this.itm = itm;
	}

	@Override
	public String toString()
	{
		return "Items [itm=" + itm + "]";
	}

	
	
	
}
