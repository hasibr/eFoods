import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Middleware {
	public Middleware() {

	}
	
	/**
	 * A class that helps consolidate POs of new orders. The POtotal file is renewed after each run.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	public void merge2() throws SAXException, IOException, ParserConfigurationException, TransformerException {
		//point variable "dir" to the directory of POs
		File dir = new File("POs/");
		//Array rootfiles[] now containes the files in the directory of POs
		File[] rootFiles = dir.listFiles();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = null;
		String myname = "";
		//Making an Instance of the consolidated purchase class
		PurchCons myFinal = PurchCons.getInstance();
		//Emptys the Map attribute of PurchCons class to avois reconsolidation of previously done POs
		myFinal.empty();

		// for (File f : rootFiles) {
		// System.out.println(f.getAbsolutePath());
		// }
		
		/**
		 * 
		 */
		//For each file in our POs directory
		for (File f : rootFiles) {
			//If the PO has not yet been consolidated
			if (!(f.getAbsolutePath().contains("_done.xml"))) {
				doc = db.parse(f.getAbsolutePath());
				// System.out.println(rootFiles[i].getAbsolutePath());

				// Makes a list of all items in the current P/O
				NodeList myItems = doc.getElementsByTagName("item");

				// Iterating through each item
				for (int k = 0; k < myItems.getLength(); k++) {
					// Iterating through the childnodes of each item
					for (int j = 0; j < myItems.item(k).getChildNodes().getLength(); j++) {
						// the current item num
						String item = myItems.item(k).getAttributes().getNamedItem("number").getTextContent();
						// if the map has the curr item
						if (myFinal.containsItem(item)) {
							// if the curr child node has the name "name"
							if (myItems.item(k).getChildNodes().item(j).getNodeName().equals("name")) {
								myname = myname + myItems.item(k).getChildNodes().item(j).getTextContent();
								// System.out.println(myname);
							}
							// if the curr child node has the name "quantity"
							if (myItems.item(k).getChildNodes().item(j).getNodeName().equals("quantity")) {
								// this adds the curr quantity of item to the total quantity of item in the map 
								myFinal.addQuantity(item, myname,
										Integer.parseInt(myItems.item(k).getChildNodes().item(j).getTextContent()));
								//empty name so that for next run, it can have the name of next item
								myname = new String("");
							}

						}
						// If this is the first time it is seeing this item.
						else {
							
							//when you see the tag "name"
							if (myItems.item(k).getChildNodes().item(j).getNodeName().equals("name")) {
								//store it's text content in variable "myname"
								myname = new String(myItems.item(k).getChildNodes().item(j).getTextContent());
							}
							

							if (myItems.item(k).getChildNodes().item(j).getNodeName().equals("quantity")) {
								
								// Add the current item number and its quantity to the map
								myFinal.additemNumber(item, myname,
										Integer.parseInt(myItems.item(k).getChildNodes().item(j).getTextContent()));
								myname = new String("");

							}
						}
					}

				}

			}
		}
		//Calls the method in Purchcons class that writes Consolidated file
		myFinal.write();
		

	}

	public void cleanDirectory() {
		//Called after files have been consolidated in the main method
		File dir = new File("POs/");
		File[] rootFiles = dir.listFiles();

		for (File f : rootFiles) //For each file
		{
			//If it has not yet been marked as done
			if (!f.getAbsolutePath().contains("_done.xml")) {
				//mark already consolidated POs by adding "_done" to name of file
				f.renameTo(new File(f.getAbsolutePath().replace(".xml", "_done.xml")));
			}

		}

	}

}
