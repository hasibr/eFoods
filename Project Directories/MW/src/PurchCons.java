import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PurchCons {
	//Map object to hold the itemNumber and a list holding corresponding name & quantity
	private Map <String,ArrayList<Object>> myMap = new HashMap<>();

	private static PurchCons instance = null;
	
	public PurchCons() {
		
	}
	//Singleton instance
	public synchronized static PurchCons getInstance() {
		if(instance == null) {
			instance = new PurchCons();
		}
		
		return instance;
	}
	//Method to add item details to map
	public void additemNumber(String itemNumber, String name, int quantity) {
		ArrayList<Object> dets = new ArrayList<Object>();
		//The dets arraylist holds the name of the item and the quantity of the item
		dets.add(0, name);
		dets.add(1, quantity);
		//Add the itemNumber and the list holding name and quantity to the map for later extraction
		this.myMap.put(itemNumber, dets);
	}
	
	//This method sums of the quantities for all items of the same itemNumber
	public void addQuantity(String itemNumber, String name, int quantity) {
		//If the Map has the item
		if(myMap.containsKey(itemNumber)) {
			ArrayList<Object> temp = new ArrayList<Object>();
			//Store the name in a temporary list caled temp at pos 0
			temp.add(0, myMap.get(itemNumber).get(0));
			//Store the total quantity of item(previous quantity + new passed quantity) in a temporary list caled temp at pos 1
			temp.add(1, ((Integer)(myMap.get(itemNumber).get(1))+quantity));
			//replace the itemNumber values with the new ones(as quantity has increased)
			myMap.put(itemNumber,temp);
		}
	
		//System.out.println("Heyo"+myMap.get(itemNumber));
	}
	
	public void empty()
	{
		//Empty my map of consolidated POs
		myMap.clear();
	}
	
	//Check if the Map already has the itemNumber
	public boolean containsItem(String itemNumber) {
		boolean myRes = myMap.containsKey(itemNumber);
		//return "true" is the item already exists in map, "false" otherwise
		return myRes;
	}


	//Method to write Consolidated xml file for B2B
	public void write()
	{
		File dir = new File("DONE/");
		File [] rootFiles = dir.listFiles();
		int num = rootFiles.length + 1;
				
		//Get current date when files are being consolidater
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		int count = myMap.size();
		ArrayList<String> a = new ArrayList<>();
		//store all itemNumber in a list called "a"
		a.addAll(myMap.keySet());
		//formatting output to have the current date of consolidated file as opening tag
		String myoutput = "<order submitted=\""+ dateFormat.format(date).toString() + "\">";
		try
		{
		for(int i = 0; i< count; i++)
		{
			//writing consolidated file output
			myoutput = myoutput + "<item number=\"" + a.get(i) + "\"><name>" + myMap.get(a.get(i)).get(0) + "</name><quantity>"+ myMap.get(a.get(i)).get(1)+"</quantity></item>";
		}
		myoutput= myoutput + "</order>";
		//get Writer to file called "POtot.xml"
		Writer output = new BufferedWriter(new FileWriter("DONE/POtot"+num +".xml"));
        String xmlOutput = myoutput; 
      //Write the consolidated xml to file
        output.write(xmlOutput);
        //Close the buffer
        output.close();
		
		}
		catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } 
		
	}
	
	
	

}
