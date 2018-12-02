package cs4125;

import java.io.IOException;
import ui.*;
import dataPersistence.*;


public class Store {
		
	public static void main(String[] args)	throws IOException{
		//Reads files into lists
		StoreFacade f = new StoreFacade();
		f.run();
		
		System.out.println("- Login info -\nFor testing stockEmp code: ID, Password = 12");
		System.out.println("For testing manager code:  ID, Password = 13\n");
		//Launches UI
		UI store = new StoreUI();
		store.startInterface();
		
		//Reads lists into files
		DataPersistence d = new DataPersistence();
		d.run();
	}
}