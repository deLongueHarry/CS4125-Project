package ui;

import java.util.Scanner;

import cs4125.Store;
import employee.Manager;
import goods.Product;
import goods.StockItem;

public class StockEmployeeUI implements UI {

	private Scanner in = new Scanner(System.in);
	
	public void startInterface() {
		
		String error2 = "Invalid selection. Please enter 1 to order new stock, 2 to add/remove stock, 3 to view stock, or 0 to quit the program: ";
		System.out.printf("\n\nSelect an option from the following:\n %3s\n %3s\n %3s\n %3s\n\nEnter selection: ", "1. Create new stock order",
							"2. Add/remove stock to the system", "3. View current stock levels", "0. Quit program");
		
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
	public static void OrderStock() {
		
		boolean approved;
		if (LoginUI.emp instanceof Manager) {
			approved = true;
		}
		else {
			approved = false;
		}
		// 
		//do stuff here
		//
	}
	
	// Gives user options to register, add or remove stock from the system
	// Author: Michael
	private void UpdateStock() {
		
		String error1 = "Invalid selection. Please enter 1 register stock, 2 to remove stock, or 0 to quit the program: ";
		String error2 = "Invalid input, please try again!";
		String answer = "";
		
		System.out.printf("\n\nSelect an option from the following:\n %3s\n %3s\n %3s\n %3s\n\nEnter selection: ",
							"1. Register new stock", "2. Remove stock", "0. Quit program");

		boolean validSelection = false;
		while(!validSelection) {
						
			try {
				int input = Integer.parseInt(in.nextLine());
				
				// Register new product and add as stock item
				if (input == 1) {
					
					viewProducts();
					System.out.print("\nEnter the ID of the product you wish to register: ");
					try {
						
						int inputID = Integer.parseInt(in.nextLine());
						for (int i = 0; i < Store.stockItems.size(); i++) {
							
							if (inputID == Store.products.get(i).getProductID()) {
								addNewStock(Store.products.get(i));
							}
						}
						
						validSelection = true;
						startInterface();
					}
					catch (NumberFormatException ID) {
						System.out.println(error2);								
					}
				}
				// Remove stock item
				else if (input == 2) {
					
					viewStock();
					System.out.print("\nEnter the ID of the stock product you wish to remove: ");
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
	private void addNewStock(Product prod) {
		
		String error, input, useBy;
		int stockID, stockQty = 0;
		int failedAttempts = 0;
		
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
			input = in.nextLine();
			
			try {
				stockQty = Integer.parseInt(input);
				qtyDone = true;
			}
			catch (NumberFormatException q) {
				System.out.print(error);
				failedAttempts++;
			}
		}
		
		// checks if the stockItems ArrayList has at least 1 existing product item
		if (Store.stockItems.isEmpty()) {
			stockID = 1;
		}
		else {
			// assigns the entered product an ID number based on the last product ID in the list
			StockItem lastStock = Store.stockItems.get(Store.stockItems.size()-1);
			stockID = (lastStock.getItmID()) + 1;
		}
				
		Store.stockItems.add(new StockItem(stockID, prod, stockQty, useBy));
		
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
	
	private void viewProducts() {
		
		String stars = "**********************************";
		
		System.out.printf("\n%s%s%s\n\n", stars, " Available Products ", stars);
		System.out.printf("%-10s %-25s %-10s %14s\n\n", "ID", "Name", "Type", "Company");
				
		for (int i = 0; i < Store.stockItems.size(); i++) {
			System.out.printf("%-10d %-25s %-10s %14s\n", Store.stockItems.get(i).getProduct().getProductID(),
								Store.stockItems.get(i).getProduct().getProductName(), Store.stockItems.get(i).getProduct().getType(),
									Store.stockItems.get(i).getProduct().getCompany());
		}
		System.out.printf("\n%s%s%s\n\n", stars, "***************", stars);
	}	
}