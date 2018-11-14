package model;

public class Engine {
	
	private static Engine instance = null;
	
	private Engine() {
		// singleton class. should not be instantiated by outside
	}
	
	
	public synchronized static Engine getInstance() {
		
		if (instance == null) {
			
			instance =  new Engine();
		}
		
		return instance;
	}

}
