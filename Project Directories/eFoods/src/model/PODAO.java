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
	 * stores a PO.xml file on the system in a root folder in WEB-INF called POs/.
	 * 
	 * @param filename
	 * @param po
	 * @throws Exception
	 */
	public void storeFile(String filename, PO po) throws Exception {
		

		
		try {
			File f = new File(ROOT+filename);
			f.createNewFile();
			PrintStream out = new PrintStream(f);
		
			JAXBContext context = JAXBContext.newInstance(PO.class);
			Marshaller m = context.createMarshaller();
//			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); //make pretty
			m.marshal(po, out);
		}
		catch(JAXBException jbe) {
			jbe.printStackTrace();
			throw new Exception("JAXBE exception. Couldn't generate PO");
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
	
	
	/**
	 * return a list of PO's belonging to this account
	 * 
	 * @param accountName
	 * @return
	 * @throws Exception
	 */
	public List<PO> getPOs(String accountName) throws Exception{
		
		return extractPOs(files(accountName));
	}

//----------------------- helpers -------------------------------------------------
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


	
	/**
	 * creates and returns an PO beans given and xml document
	 * @param doc
	 * @return
	 */
	private PO parseDoc(Document doc, String fileName) {
		
		
		/*
		 * extract the attributes of the PO (id and date submitted)
		 */
		Element rootElement = doc.getDocumentElement();
		String id = rootElement.getAttribute("id");
		String submitted = rootElement.getAttribute("submitted");
		
		/*
		 * extract the customers information (name and account)
		 */
		NodeList customer = doc.getElementsByTagName("customer");
		Node customerNode = customer.item(0);
		Element customerElement = (Element) customerNode;
		NodeList customerChild = customerElement.getElementsByTagName("name");
		Node n = customerChild.item(0);
		
		
		String account = customerElement.getAttribute("account");
		String name = n.getTextContent();
		
		
		/*
		 * extract the items and their information
		 * 
		 * NOTE: next time use JSON...
		 */
		NodeList item = doc.getElementsByTagName("item");
		List<Item> itm = new ArrayList<>();
		
		for(int i = 0; i < item.getLength(); i++) {
			
			Node itemNode = item.item(i);
			Element itemElmenet = (Element)itemNode;
			
			String itemNum = itemElmenet.getAttribute("number"); // product number
			
			//----------------------------------------
			
			Element itemContent = (Element) item.item(i);
			
			String a = itemContent.getElementsByTagName("name").item(0).getTextContent(); //item name
			String b = itemContent.getElementsByTagName("price").item(0).getTextContent();//item price
			String c = itemContent.getElementsByTagName("category").item(0).getTextContent();//item category
			String d = itemContent.getElementsByTagName("quantity").item(0).getTextContent();//item quantity
//			String e = itemContent.getElementsByTagName("extended").item(0).getTextContent();
			
			Item bean = new Item(itemNum, a, b, c, d);//,e);
			
			itm.add(bean);
			
			
		}
		
		/*
		 * extract costs
		 */
		String total = doc.getElementsByTagName("total").item(0).getTextContent();
		String shipping = doc.getElementsByTagName("shipping").item(0).getTextContent();
		String HST = doc.getElementsByTagName("HST").item(0).getTextContent();
		String grandTotal = doc.getElementsByTagName("grandTotal").item(0).getTextContent();
		
		
		Items i = new Items();
		i.setItm(itm);
		
		
		/*
		 * set the PO attributes
		 */
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
	
	/**
	 * 
	 * 
	 * @return root directory of the POs
	 */
	public static String rootDirectory() {
		
		return ROOT;
		
	}
	
	/**
	 * 
	 * @return number of files in the root directory
	 */
	public int rootFileCount() {
		return new File(rootDirectory()).list().length;
	}
	
	/**
	 * 
	 * @param name
	 * @return the number of files in the root directory that have this name
	 */
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
	
}
