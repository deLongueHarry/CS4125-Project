package ui;

import java.util.Scanner;

import employee.Employee;
import retailStore.Account;
import retailStore.StoreFacade;
import stock.*;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StockEmployeeUI implements UI {

	private Scanner in = new Scanner(System.in);
	
	// Author: Michael
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
	
	// Enables user to create new orders
	// Author Michael
	public void OrderStock()
	{

		List<OrderItem> items = new ArrayList<> ();
		Account acc = retailStore.Account.instanceOf();
		Employee tempEmp = LoginUI.emp;
		
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");	
		String dateOrdered = sdf.format(d);		
		
		String error = "Invalid input, please enter a valid number.";
	
		boolean approved, paid;
		double totalOrderCost = 0;
		int orderID;	
		
		if (tempEmp.getType().equals("manager")) {
			approved = true;
		}
		else {
			approved = false;
		}
		
		viewProducts();
		
		boolean validSelection = false;
		while (!validSelection) {
			
			Product tempProd = null;			
			System.out.print("\nEnter the ID of the product you wish to order: ");
			try {
				int input = Integer.parseInt(in.nextLine());
				for (int i = 0; i < StoreFacade.products.size(); i++) {
					
					if (input == StoreFacade.products.get(i).getProductID()) {
						
						tempProd = StoreFacade.products.get(i);
						System.out.print("\n\n" + tempProd.getProductName()	+ " comes in boxes of "
											+ tempProd.getMinimumOrder() + ".");
						
						int orderItmID, qty = 0;
						if (StoreFacade.orderItems.isEmpty()) 
							orderItmID = 1;
						else {
							
							// assigns the entered product an ID number based on the last product ID in the list
							OrderItem lastOrderItem = StoreFacade.orderItems.get(StoreFacade.orderItems.size()-1);
							orderItmID = (lastOrderItem.getItmID()) + 1;
						}		
						
						boolean qtyDone = false;
						while(!qtyDone) {
							 System.out.print("\nEnter the no. of boxes you wish to order: ");
							 qty = Integer.parseInt(in.nextLine());
							 
							 if (qty > 0)
								 qtyDone = true;
							 else {
								 System.out.print("\nInvalid input. Enter a positive number of boxes to order: ");
							 }
			
						}
						int totalQty = tempProd.getMinimumOrder() * qty;
						totalOrderCost += (tempProd.getCostPrice() * totalQty);
						
						OrderItem tempOI = new OrderItem(orderItmID, tempProd, totalQty);
						StoreFacade.orderItems.add(tempOI);
						items.add(tempOI);
					}
				}
				
			} catch (NumberFormatException p) {
				System.out.println(error);
			}
			
			System.out.print("\n\nFinished ordering? (Y/y/N/n) ");
			String done = in.nextLine();
			if (done.equalsIgnoreCase("y")) {
				validSelection = true;
			}			
			else
				validSelection = false;	
		}
		
		// checks if the orderItems List has at least 1 existing product item
		if (StoreFacade.orders == null) 
			orderID = 1;
		else {
			// assigns the entered order an ID number based on the last order ID in the list
			Order lastOrder = StoreFacade.orders.get(StoreFacade.orders.size()-1);
			orderID = (lastOrder.getOrderID()) + 1;
			
			if (approved)
				paid = acc.paymentSuccessful(totalOrderCost);
			else
				paid = false;
			StoreFacade.orders.add(new Order(orderID, items, dateOrdered, tempEmp, approved, paid));
		}		
		
		if (approved) {
			UI next = new ManagerUI();
			next.startInterface();	
		}
		else
			startInterface();
		
	}

	
	// Gives user options to register, add or remove stock from the system
	// Author: Michael
	private void UpdateStock() {
		
		String error1 = "Invalid selection. Please enter 1 register stock, 2 to remove stock, or 0 to quit the program: ";
		String error2 = "Invalid input, please try again!";
		
		System.out.printf("\n\nSelect an option from the following:\n %3s\n %3s\n %3s\n\nEnter selection: ",
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
						for (int i = 0; i < StoreFacade.stockItems.size(); i++) {
							
							if (inputID == StoreFacade.products.get(i).getProductID()) {
								addNewStock(StoreFacade.products.get(i));
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
						for (int i = 0; i < StoreFacade.stockItems.size(); i++) {
							
							if (inputID == StoreFacade.products.get(i).getProductID()) {
								StoreFacade.stockItems.remove(i);
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
		if (StoreFacade.stockItems.isEmpty()) {
			stockID = 1;
		}
		else {
			// assigns the entered product an ID number based on the last product ID in the list
			StockItem lastStock = StoreFacade.stockItems.get(StoreFacade.stockItems.size()-1);
			stockID = (lastStock.getItmID()) + 1;
		}
				
		StoreFacade.stockItems.add(new StockItem(stockID, prod, stockQty, useBy));
		
	}
	
	// Prints the current stock information on the console
	// Author: Michael
	private void viewStock() {
		
		String euro = "\u20ac";
		String stars = "*************************************";
		
		System.out.printf("\n%s%s%s\n\n", stars, " Current Stock ", stars);
		System.out.printf("%-10s %-25s %-10s %14s %10s %15s\n\n", "ID", "Name", "Type", "Price", "Qty", "Expiry Date");
				
		for (int i = 0; i < StoreFacade.stockItems.size(); i++) {
			Product temp = StoreFacade.stockItems.get(i).getProduct();
			
			System.out.printf("%-10d %-25s %-10s %10s%.2f %10d %15s\n", temp.getProductID(), temp.getProductName(), temp.getType(), euro,
								StoreFacade.stockItems.get(i).getPrice(), StoreFacade.stockItems.get(i).getQty(), StoreFacade.stockItems.get(i).getUseBy());
		}
		System.out.printf("\n%s%s%s\n\n", stars, "***************", stars);
	}	
	
	// Prints the current product information on the console
	// Author: Michael
	private void viewProducts() {
		
		String stars = "**********************************";
		
		System.out.printf("\n%s%s%s\n\n", stars, " Available Products ", stars);
		System.out.printf("%-10s %-25s %-10s %14s\n\n", "ID", "Name", "Type", "Company");
				
		for (int i = 0; i < StoreFacade.stockItems.size(); i++) {
			Product temp = StoreFacade.stockItems.get(i).getProduct();
			
			System.out.printf("%-10d %-25s %-10s %14s\n", temp.getProductID(), temp.getProductName(), 
								temp.getType(), temp.getCompany());
		}
		System.out.printf("\n%s%s%s\n\n", stars, "***************", stars);
	}	
}