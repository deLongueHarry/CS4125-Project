package ui;

import java.util.Scanner;
import java.util.ArrayList;
import goods.*;
import other.Store;

public class StockEmployeeUI implements UI {

	private Scanner in = new Scanner(System.in);
	
	public void startInterface() {
		
		String error2 = "Invalid selection. Please enter 1 to order new stock, 2 to add/remove stock, 3 to view stock, or 0 to quit the program: ";
		System.out.printf("\n\nSelect an option from the following:\n %3s\n %3s\n %3s\n %3s\n\nEnter selection: ", "1. Create new stock order", "2. Add/remove stock to the system", "3. View current stock levels", "0. Quit program");
		
		boolean validSelection = false;
		while(!validSelection) {
			try {
				
				int input = Integer.parseInt(in.nextLine());
				if (input == 1) {
					
					validSelection = true;
					OrderStock();
				}
				else if (input == 2) {
					
					validSelection = true;
					UpdateStock();
				}
				else if (input == 3) {
					
					viewStock();
					System.out.print("Enter a new selection: ");
				}
				else if (input == 0) {
					break;
				}
				else {
					System.out.print(error2);
				}
			} 
			catch (NumberFormatException e) {
				System.out.print(error2);
			}
		}
	}
	
	// Test method
	private void OrderStock() {
		System.out.println(Store.products.size());
		System.out.println(other.Store.products.size()-1);
		
	}
	
	// Gives user options to register, add or remove stock from the system
	// Author: Michael
	private void UpdateStock() {
		
		String error1 = "Invalid selection. Please enter 1 register stock, 2 to add stock, 3 to remove stock, or 0 to quit the program: ";
		String error2 = "Invalid input, please try again!";
		String answer = "";
		
		System.out.printf("\n\nSelect an option from the following:\n %3s\n %3s\n %3s\n %3s\n\nEnter selection: ",
							"1. Register new stock", "2. Add to existing stock", "3. Remove stock", "0. Quit program");

		boolean validSelection = false;
		while(!validSelection) {
						
			try {
				int input = Integer.parseInt(in.nextLine());
				viewStock();
				
				// Register new product and add as stock item
				if (input == 1 || input == 2) {

					if (input == 1) {
						System.out.print("\n************************************\n\nDoes product exist? (Enter Y/y/N/n): ");
						answer = in.nextLine();
					}
					
					// Add existing product as a new stock item
					if (input == 2 || answer.equalsIgnoreCase("Y")) {
						
						// If product already exists all relevant data will be passed 
						// 	to addNewStock based on a matching product ID given by the user 
						System.out.print("\nEnter the ID of the product you wish to restock: ");
						try {
							
							int inputID = Integer.parseInt(in.nextLine());
							for (int i = 0; i < Store.stockItems.size(); i++) {
								
								if (inputID == Store.products.get(i).getProductID()) {
									addNewStock(Store.products.get(i).getProductName(), 
											Store.products.get(i).getType(), Store.products.get(i).getCompany());
								}
							}
							
							validSelection = true;
							startInterface();
						}
						catch (NumberFormatException ID) {
							System.out.println(error2);								
						}
					}
					else if (answer.equalsIgnoreCase("N")) {
							
						System.out.print("\n\n******* Enter new product **********\n\nProduct name: ");
						String prodName = in.nextLine();
						
						System.out.print("Classification: ");
						String prodType = in.nextLine();
						
						System.out.print("Manufacturer: ");
						String prodManufac = in.nextLine();
						
						addNewStock(prodName, prodType, prodManufac);								
							
						validSelection = true;
						startInterface();
						
					}
					else {
						System.out.print("\nIncorrect selection (Enter Y/y/N/n): ");
					}
				}
				
				// Remove stock item
				else if (input == 3) {
					
					System.out.print("\nEnter the ID of the product you wish to remove: ");
					try {
						
						int inputID = Integer.parseInt(in.nextLine());
						for (int i = 0; i < Store.stockItems.size(); i++) {
							
							if (inputID == Store.products.get(i).getProductID()) {
								Store.stockItems.remove(i);
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
	
	// Add new stock to the system
	// Author: Michael
	private void addNewStock(String prodName, String prodType, String prodManufac) {
		
		String error, strP, strQ, useBy;
		int prodID, stockQty = 0;
		int failedAttempts = 0;
		double stockPrice = 0;
		
		System.out.print("Item useby date: ");
		useBy = in.nextLine();
		
		error = "\nInvalid input, please try again!\n";
		
		boolean qtyDone = false;
		while (!qtyDone) {		
			if (failedAttempts >= 3) {			
				
				System.out.print("Would you like to quit? (Enter Y/y/N/n) ");
				failedAttempts = 0;
				
				if (in.nextLine().equalsIgnoreCase("Y")) {
					break;
				}
			}
			System.out.print("Item Quantity: ");
			strQ = in.nextLine();
			
			try {
				stockQty = Integer.parseInt(strQ);
				qtyDone = true;
			}
			catch (NumberFormatException q) {
				System.out.print(error);
				failedAttempts++;
			}
		}
		
		boolean priceDone = false;
		while (!priceDone) {
			if (failedAttempts >= 3) {			
				
				System.out.print("Would you like to quit? (Enter Y/y/N/n) ");
				failedAttempts = 0;
				
				if (in.nextLine().equalsIgnoreCase("Y")) {
					break;
				}
			}		
			System.out.print("Item Price: ");
			strP = in.nextLine();
			
			try {
				stockPrice = Double.parseDouble(strP);							
				priceDone = true;
			}
			catch (NumberFormatException p) {
				System.out.print(error);
				failedAttempts++;
			}
		}
		
		// checks if the products ArrayList has at least 1 existing product item
		if (Store.products.isEmpty()) {
			prodID = 1;
		}
		else {
			// assigns the entered product an ID number based on the last product ID in the list
			Product lastProd = Store.products.get(Store.products.size()-1);
			prodID = (lastProd.getProductID()) + 1;
		}
		
		// allows employee to add allergen information
		ArrayList<String> allergens = new ArrayList<>();;
		System.out.println("Does this product have allergen warnings? Y/N");
		String choice = in.nextLine().toLowerCase();
		 switch (choice)	{
			 case "n":
			 break;
			 
			 case "y":
			 boolean done = false;
			 
			 while(!done)	{
				 System.out.println("Please enter an allergen associated with this product, or 0 if you are finished inputting allergens.");
				 String input = in.nextLine().toLowerCase();
				 if (!(input.equals("done)")))	{
					 System.out.println("Are you sure that " + input + " is an allergy in " + prodName + "? Please enter Y/N");
					 String yn = in.nextLine().toLowerCase();
					 if (yn == "y")	{
						allergens.add(input);
					 }
				 }
				 else
					 done = true;
			 }
			 
			 break;
			 
			 default:
			 System.out.println("Please give your input in the form of Y or N");
		 }
		
		// creates new product item
		Product currentProd = new Product(prodID, 1, 30.0, prodName, prodType, prodManufac, allergens);
		
		// adds it both to the arraylist of products and stock items
		Store.products.add(currentProd);
		Store.stockItems.add(new StockItem(1, currentProd, stockQty, stockPrice, useBy));
		
		/* test loop to see if product was added to arraylist
		for  (int i = 0; i < Store.products.size(); i++) {
			System.out.println(Store.products.get(i).getProductID() + ": " 
									+ Store.products.get(i).getProductName());
		}	*/
	}
	
	// Prints the current stock information on the console
	// Author: Michael
	private void viewStock() {
		
		String euro = "\u20ac";
		String stars = "*************************************";
		
		System.out.printf("\n%s%s%s\n\n", stars, " Current Stock ", stars);
		System.out.printf("%-10s %-25s %-10s %14s %10s %15s\n\n", "ID", "Name", "Type", "Price", "Qty", "Expiry Date");
				
		for (int i = 0; i < Store.stockItems.size(); i++) {
			System.out.printf("%-10d %-25s %-10s %10s%.2f %10d %15s\n", Store.stockItems.get(i).getProduct().getProductID(),
					Store.stockItems.get(i).getProduct().getProductName(), Store.stockItems.get(i).getProduct().getType(),
						euro, Store.stockItems.get(i).getPrice(), Store.stockItems.get(i).getQty(), Store.stockItems.get(i).getUseBy());
		}
		System.out.printf("\n%s%s%s\n\n", stars, "***************", stars);
	}		

}