package beans;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
