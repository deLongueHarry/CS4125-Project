package ui;

import java.util.Scanner;
public class ManagerUI implements UI {

	private Scanner in = new Scanner(System.in);
	
	public void startInterface() {
		
		String error = "Invalid selection. Please enter 1 to order new stock, 2 to update customers database, 3 to update employees database, 4 to view requests or 0 to quit the program: ";
		System.out.printf("\n\nSelect an option from the following:\n %3s\n %3s\n %3s \n %3s \n %3s\n\nEnter selection: ", "1. Create new stock order",
							"2. Update customer database", "3. Update employee database", "4. View outstanding requests", "0. Quit program");
		
		boolean validSelection = false;
		while(!validSelection) {
			try {
				
				int input = Integer.parseInt(in.nextLine());
				if (input == 1) {
					
					validSelection = true;
					StockEmployeeUI.OrderStock();
				}
				else if (input == 2) {
					
					validSelection = true;
					UpdateCustomers();
				}
				else if (input == 3) {
					
					validSelection = true;
					UpdateEmployees();
				}
				else if (input == 4) {
					
					validSelection = true;
					ViewRequests();
				}
				else if (input == 0) {
					break;
				}
				else {
					System.out.print(error);
				}
			} 
			catch (NumberFormatException e) {
				System.out.print(error);
			}
		}
	}
	
	private void UpdateCustomers() {
		
	}
	
	private void UpdateEmployees() {
		
	}
	
	private void ViewRequests() {
		
	}

}
