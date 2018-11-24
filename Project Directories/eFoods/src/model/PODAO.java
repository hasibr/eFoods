package model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import beans.Customer;
import beans.Item;
import beans.Items;
import beans.PO;

public class PODAO{
	
	private final static String ROOT = "POs/";
	
	public PODAO() {
		
	}
	
	/**
	 * stores a PO.xml file on the system.
	 * @param filename
	 * @param po
	 * @throws Exception
	 */
	public void storeFile(String filename, PO po) throws Exception {
		
//		String filePath = "POs/PO.xml";
		try {
			File f = new File(ROOT+filename);
			f.createNewFile();
			PrintStream out = new PrintStream(f);
		
			JAXBContext context = JAXBContext.newInstance(PO.class);
			Marshaller m = context.createMarshaller();
//			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(po, out);
		}
		catch(JAXBException jbe) {
			jbe.printStackTrace();
			throw new Exception("JAXBE exception thrown. Couldn't generate PO");
		}
		catch(FileNotFoundException fnf) {
			fnf.printStackTrace();
			throw new Exception("File not found. PO could not be created.");
		}
		
	}
	
	/**
	 * returns a list of files that contain the specified name
	 * @param name
	 * @return
	 */
	public List<File> files(String name){
		
		class FFilter implements FilenameFilter{

			@Override
			public boolean accept(File dir, String n)
			{
				if (n.contains(name))
					return true;
				return false;
			}	
		}
		
		return Arrays.asList(new File(rootDirectory()).listFiles(new FFilter()));
	}
	
	public List<PO> getPOs(String accountName) throws Exception{
		
		return extractPOs(files(accountName));
	}
	
	/**
	 * returns a list of PO beans given a list of files
	 * @param poFiles
	 * @return
	 * @throws Exception
	 */
	private List<PO> extractPOs(List<File> poFiles) throws Exception{
		
		List<PO> p = new ArrayList<>();
		
		
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			
			for(File f : poFiles) {
				
				String xml = "";
				Scanner x = new Scanner(new File(PODAO.rootDirectory()+f.getName()));
				xml = x.nextLine();
				x.close();
				
				InputStream is = new ByteArrayInputStream(xml.getBytes());
				Document d = db.parse(is);
				
				p.add(parseDoc(d,f.getName().replace(".xml", "")));
				
			}
		}
		catch(ParserConfigurationException pce) {
			pce.printStackTrace();
			throw new Exception("could not make document builder");
			
		}
		catch(FileNotFoundException fnf) {
			fnf.printStackTrace();
			throw new Exception("customer PO could not be located");
		} 
		catch (SAXException e){
			e.printStackTrace();
			throw new Exception("error occurred parsing PO");
		} 
		catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("error occurred reading PO");
		}
		
		return p;
	}

//----------------------- helpers -------------------------------------------------
	
	/**
	 * creates and returns an PO beans given and xml document
	 * @param doc
	 * @return
	 */
	private PO parseDoc(Document doc, String fileName) {
		
		
		Element rootElement = doc.getDocumentElement();
		String id = rootElement.getAttribute("id");
		String submitted = rootElement.getAttribute("submitted");
		
		
		NodeList customer = doc.getElementsByTagName("customer");
		Node customerNode = customer.item(0);
		Element customerElement = (Element) customerNode;
		NodeList customerChild = customerElement.getElementsByTagName("name");
		Node n = customerChild.item(0);
		
		
		String account = customerElement.getAttribute("account");
		String name = n.getTextContent();
		
		NodeList item = doc.getElementsByTagName("item");
		List<Item> itm = new ArrayList<>();
		
		for(int i = 0; i < item.getLength(); i++) {
			
			Node itemNode = item.item(i);
			Element itemElmenet = (Element)itemNode;
			
			String itemNum = itemElmenet.getAttribute("number");
//			System.out.println(itemNum);
			
			//----------------------------------------
			
			Element itemContent = (Element) item.item(i);
			
			String a = itemContent.getElementsByTagName("name").item(0).getTextContent();
			String b = itemContent.getElementsByTagName("price").item(0).getTextContent();
			String c = itemContent.getElementsByTagName("quantity").item(0).getTextContent();
//			String d = itemContent.getElementsByTagName("extended").item(0).getTextContent();
//			System.out.println(a);
//			System.out.println(b);
//			System.out.println(c);
//			System.out.println(d);
			
			Item bean = new Item(itemNum, a, b, c);
			
			itm.add(bean);
			
			//System.out.println(itemContent.getTextContent());
			
		}
		
		String total = doc.getElementsByTagName("total").item(0).getTextContent();
		String shipping = doc.getElementsByTagName("shipping").item(0).getTextContent();
		String HST = doc.getElementsByTagName("HST").item(0).getTextContent();
		String grandTotal = doc.getElementsByTagName("grandTotal").item(0).getTextContent();
		
		
//		System.out.println(id);
//		System.out.println(submitted);
//		System.out.println(account);
//		System.out.println(name);
//		
//		System.out.println(itm.toString());
//		System.out.println(total);
//		System.out.println(shipping);
//		System.out.println(HST);
//		System.out.println(grandTotal);
		
		Items i = new Items();
		i.setItm(itm);
		
		PO po = new PO();
		
		po.setCustomer(new Customer(account, name, "N/A"));
		po.setItems(i);
		
		po.setTotal(total);
		po.setShipping(shipping);
		po.setHST(HST);
		po.setGrandTotal(grandTotal);
		po.setId(id);
		po.setSubmitted(submitted);
		
		po.setFileName(fileName);
		
		
		
		
		
		
		return po;
	}
	
	public static String rootDirectory() {
		
		return ROOT;
		
	}
	
	public int rootFileCount() {
		return new File(rootDirectory()).list().length;
	}
	
	public int filesWithName(String name) {
		
		class FFilter implements FilenameFilter{

			@Override
			public boolean accept(File dir, String n)
			{
				if (n.contains(name))
					return true;
				return false;
			}	
		}
		
		return new File(rootDirectory()).listFiles(new FFilter()).length;
		
	}
	
//	public static void main(String[] args) throws Exception {
//		
//		PODAO p = new PODAO();
//		
//		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//		
//		String xml = "";
//		Scanner x = new Scanner(new File(PODAO.rootDirectory()+"po_franciso01.xml"));
//		while(x.hasNextLine())
//			xml += x.nextLine();
//		x.close();
//		
//		InputStream is = new ByteArrayInputStream(xml.getBytes());
//		Document d = db.parse(is);
//		
//		System.out.println(p.parseDoc(d,"po_franciso01.xml"));
//	}
	
}
