package retailStore;

import java.io.IOException;
import java.text.ParseException;

public class Store {
		
	public static void main(String[] args)	throws IOException, ParseException{

		// For testing purposes
		System.out.println("- Login info -\n\nFor testing stockEmp code: ID, Password = 12\n"
								+ "For testing manager code: ID, Password = 13\n"
									+ "For testing customer code: ID = 1001\n\n");
		
		StoreFacade f = new StoreFacade();
		f.run();	
	}
}