package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import beans.PO;

public class PODAO{
	
	private final String ROOT = "POs/";
	
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
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
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

//----------------------- helpers -------------------------------------------------
	public String rootDirectory() {
		
		return this.ROOT;
		
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
	
}
