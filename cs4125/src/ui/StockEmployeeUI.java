package ui;

import java.util.Scanner;

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
	
	//Creates Order
	//author: Harry
	public void OrderStock()
	{
		boolean approved;
		boolean paid;
		if (LoginUI.emp.getType().equals("manager")) {
			approved = true;
			paid = true;
		}
		else {
			approved = false;
			paid = false;
		}
		if (approved)	{
			List<OrderItem> items = new ArrayList<> ();
			boolean check = false;
			String input = "";
			System.out.println("Enter order ID: ");
			int orderID = Integer.parseInt(in.nextLine());
			while(!check)
			{
				String checker = createOrder();
				if(!(checker.equals("done")))
				{
					input += checker;
				}
				else
					check = true;
			}
			//converts returned result from createOrder to a string array and splits at every name/price
			String[] inputArray = input.split(",");
			for(int i = 0; i < inputArray.length; i += 2)
			{
				for(int j = 0; j < StoreFacade.products.size(); j++)
				{
					if(inputArray[i].equals(StoreFacade.products.get(j).getProductName()))
					{
						OrderItem oItm = new OrderItem(j, StoreFacade.orderItems.get(j).getProduct(), StoreFacade.orderItems.get(j).getQty());
						items.add(oItm);
					}
				}
			}
			Date d = new java.util.Date();
			SimpleDateFormat sdf = new SimpleDateFormat("ddmmyy");
			String dateOrdered = sdf.format(d);
			
			StoreFacade.orders.add(new Order(orderID, items, dateOrdered, LoginUI.emp, approved, paid));
		}
	}
	
	//Finds list of order items
	//Author: Harry
	private String createOrder()
	{		
		System.out.println("Press 1 to place an Order\n Press 0 to Quit\n");
		int newOrder = Integer.parseInt(in.nextLine());
		if (newOrder == 1)
		{
			boolean valid = false;
			System.out.println("Enter items to be ordered");
			String out = "";
			String choice = "";
			//Print product names from txt file(products.txt)
			for(int i = 0; i < StoreFacade.products.size(); i++)
			{
				viewProducts();
			}	
			System.out.println("Please input products from the list above to be ordered. When you are finished, enter 0");
			//reads user input and writes chosen products to text file order.txt
			try{
					choice = in.nextLine().toLowerCase();
				} catch (Exception e) {
					System.out.println("Input does not match products");
				}
				switch(choice)
				{
				case "0":
					out = "done";
					System.out.println("order has been placed.");
					break;
				default:
					for (int i = 0; i < StoreFacade.products.size(); i++)	{
						if (choice.matches(StoreFacade.products.get(i).getProductName().toLowerCase()))	{
							valid = true;
							}
						}	
					break;
				}
					if (!(out.equals("done")) && valid)	{
						out += choice + ",";
						try	{
								System.out.println("Please enter amount of this item to be ordered.");
								out += in.nextLine();
							}
							catch(NumberFormatException e) {
								System.out.println("Please give your input in the form of a number.");
							}
					}
				
					else if (!(out.equals("done")) && !(valid))	
					{
						System.out.println("Input does not match any products. Please try again");
					}
					return out;
				}
		return "";
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