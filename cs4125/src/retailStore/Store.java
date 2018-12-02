package retailStore;

import java.io.IOException;
import java.text.ParseException;

public class Store {
		
	public static void main(String[] args)	throws IOException, ParseException{

		// For testing purposes
		System.out.println("- Login info -\nFor testing stockEmp code: ID, Password = 12");
		System.out.println("For testing manager code:  ID, Password = 13\n");
		
		StoreFacade f = new StoreFacade();
		f.run();	
	}
}