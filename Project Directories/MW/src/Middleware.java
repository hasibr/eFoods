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

	public void merge2() throws SAXException, IOException, ParserConfigurationException, TransformerException {
		File dir = new File("POs/");
		File[] rootFiles = dir.listFiles();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = null;
		String myname = "";
		PurchCons myFinal = PurchCons.getInstance();
		myFinal.empty();

		// for (File f : rootFiles) {
		// System.out.println(f.getAbsolutePath());
		// }

		for (File f : rootFiles) {
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
								// System.out.println(myname);
								// System.out.println(item);
								// System.out.println("In map " +
								// myItems.item(k).getChildNodes().item(j).getTextContent());
								// this adds the curr quantity to the total quant in the map
								myFinal.addQuantity(item, myname,
										Integer.parseInt(myItems.item(k).getChildNodes().item(j).getTextContent()));
								myname = new String("");
							}

						} else {
							if (myItems.item(k).getChildNodes().item(j).getNodeName().equals("name")) {
								myname = new String(myItems.item(k).getChildNodes().item(j).getTextContent());
							}
							// If this is the first time it is seeing this item.

							if (myItems.item(k).getChildNodes().item(j).getNodeName().equals("quantity")) {
								// System.out.println(item);
								// System.out.println("outside map " +
								// myItems.item(k).getChildNodes().item(j).getTextContent());
								// Add the curr item num and its quan to the map
								myFinal.additemNumber(item, myname,
										Integer.parseInt(myItems.item(k).getChildNodes().item(j).getTextContent()));
								myname = new String("");

							}
						}
					}

				}

			}
		}
		myFinal.write();

	}

	public void cleanDirectory() {
		File dir = new File("POs/");
		File[] rootFiles = dir.listFiles();

		for (File f : rootFiles) // move consolidated P/O files to Done directory
		{
			if (!f.getAbsolutePath().contains("_done.xml")) {

				f.renameTo(new File(f.getAbsolutePath().replace(".xml", "_done.xml")));
			}

		}

	}

}
