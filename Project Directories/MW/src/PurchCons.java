import java.io.BufferedWriter;
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
	//private Map <String, Integer> myMap = new HashMap<String, Integer>();
	private Map <String,ArrayList<Object>> myMap = new HashMap<>();

	private static PurchCons instance = null;
	
	public PurchCons() {
		
	}
	
	public synchronized static PurchCons getInstance() {
		if(instance == null) {
			instance = new PurchCons();
		}
		
		return instance;
	}
	
	public void additemNumber(String itemNumber, String name, int quantity) {
		ArrayList<Object> dets = new ArrayList<Object>();
		dets.add(0, name);
		dets.add(1, quantity);

		this.myMap.put(itemNumber, dets);
		//dets.clear();
	}
	/*
	public void setitemName(String given) {
		name = new String(given);
	}
	*/
	public void addQuantity(String itemNumber, String name, int quantity) {
		if(myMap.containsKey(itemNumber)) {
			ArrayList<Object> temp = new ArrayList<Object>();
			temp.add(0, myMap.get(itemNumber).get(0));
			temp.add(1, ((Integer)(myMap.get(itemNumber).get(1))+quantity));
			myMap.put(itemNumber,temp);
		}
	
		//System.out.println("Heyo"+myMap.get(itemNumber));
	}
	
	public void empty()
	{
		myMap.clear();
	}
	public boolean containsItem(String itemNumber) {
		boolean myRes = myMap.containsKey(itemNumber);
		return myRes;
	}

	/*
	@Override
	public String toString() 
	{
		
		for (String name: myMap.keySet())
		{

            String key =name.toString();
            String valuename = myMap.get(name).get(0).toString();  
            String valuequant = myMap.get(name).get(1).toString();
           // System.out.println(key + " " + valuename + " " + valuequant);
            System.out.print(objectToString());
		} 
		return "PurchCons [myMap=" + myMap + "]";
		
	}
	*/
	
	public void write()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		int count = myMap.size();
		ArrayList<String> a = new ArrayList<>();
		a.addAll(myMap.keySet());
		String myoutput = "<order submitted=\""+ dateFormat.format(date).toString() + "\">";
		try
		{
		for(int i = 0; i< count; i++)
		{
			myoutput = myoutput + "<item number=\"" + a.get(i) + "\"><name>" + myMap.get(a.get(i)).get(0) + "</name><quantity>"+ myMap.get(a.get(i)).get(1)+"</quantity></item>";
		}
		myoutput= myoutput + "</order>";
		Writer output = new BufferedWriter(new FileWriter("DONE/POtot.xml"));
        String xmlOutput = myoutput; 
        output.write(xmlOutput);
        output.close();
		
		}
		catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } 
		
	}
	
	
	

}
