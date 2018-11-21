package ui;

import java.util.ArrayList;
import java.util.Scanner;

import cs4125.Store;
import customer.Customer;
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
		
		String error1 = "Invalid selection. Please enter 1 register new customer, 2 to remove customer from database, or 0 to quit the program: ";
		String error2 = "Invalid input, please try again!";
		int custID;
		
		System.out.printf("\n\nSelect an option from the following:\n %3s\n %3s\n %3s\n %3s\n\nEnter selection: ",
							"1. Register new customer", "2. Remove customer", "0. Quit program");

		boolean validSelection = false;
		while(!validSelection) {
						
			try {
				int input = Integer.parseInt(in.nextLine());
				
				// Register new customer
				if (input == 1) {
					
					if (Store.stockItems.isEmpty()) {
						custID = 1;
					}
					else {
						// assigns the entered product an ID number based on the last product ID in the list
						Customer lastCust = Store.customers.get(Store.customers.size()-1);
						custID = (lastCust.getCustID()) + 1;
					}
					
					System.out.print("\nCustomer ID: " + custID + "\nCustomer name: ");
					String custName = in.nextLine();
					System.out.print("\nLoyalty points: " + 0 + "\nAllergens: ");
					String allergen = in.nextLine();
					
					ArrayList<String> allergens = new ArrayList<String>();
					System.out.print("\nCredit card no: ");
					String creditCard = in.nextLine();
					
					Customer temp = new Customer(custID, custName, 0, allergens, creditCard, null);
				}
				// Remove customer from database
				else if (input == 2) {
					
					System.out.print("\nEnter the ID of the customer you wish to remove: ");
					try {
						
						int inputID = Integer.parseInt(in.nextLine());
						for (int i = 0; i < Store.customers.size(); i++) {
							
							if (inputID == Store.customers.get(i).getCustID()) {
								Store.customers.remove(i);
							}
						}
						
						validSelection = true;
						startInterface();
					}
					catch (NumberFormatException ID) {
						System.out.println(error2);								
					}
				}
				
				// To quit the program
				else if (input == 0) {
					break;
				}
				else {
					System.out.print(error1);
				}
			} 
			catch (NumberFormatException e) {
				System.out.print(error1);
			}
		}
	}
	
	private void UpdateEmployees() {
		
	}
	
	private void ViewRequests() {
		
	}

}
