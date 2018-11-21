package ui;

import employee.*;
import goods.Order;
import goods.OrderItem;
import goods.Product;
import cs4125.Store;

import java.util.Scanner;

public class LoginUI implements UI {

	public static Employee emp;		// Current employee logged in to the system
	private Scanner in = new Scanner(System.in);
	
	public void startInterface() {
		
		System.out.print("\n********** Employee Login **********\n");
		
		int inputID = 0;
		int failedAttempts = 0;
		
		boolean validLogin = false;
		while (!validLogin) {
			
			if (failedAttempts >= 3) {
				
				System.out.print("Would you like to quit? (Enter Y/y/N/n) ");
				failedAttempts = 0;
				
				if (in.nextLine().equalsIgnoreCase("Y")) {
					break;
				}
			}
			
			try {
				System.out.print("\nLogin ID: ");
				inputID = Integer.parseInt(in.nextLine());
			}
			catch (NumberFormatException e) {
				
			}
			
			System.out.print("Password: ");
			String inputPass = in.nextLine();	
			
			validLogin = checkValidLogin(inputID, inputPass);
			emp = getCurrentEmployee(inputID);
			
			if (validLogin) {
				
				UI currentUI = null;
				
				System.out.printf("\n\n************* %d *************\n\nLogged in as: %s", emp.getID(), emp.getName());
				
				if (emp instanceof Manager) {
					currentUI = new ManagerUI();
				}
				if (emp instanceof StockEmployee) {
					currentUI = new StockEmployeeUI();
				}
				
				
				currentUI.startInterface();
				in.close();
			}
			
			failedAttempts++;
		}
	}
	
	// Checks if the login information matches a current employee in the database
	// Author: Michael
	private boolean checkValidLogin(int inputID, String inputPass) {

		for (int i = 0; i < Store.employees.size(); i++) {
			if (inputID == Store.employees.get(i).getID()) {
				
				if (inputPass.equals(Store.employees.get(i).getPassword())) {
					return true;
				}
			}	
		}
		
		System.out.println("\nInvalid username and/or password. Please try again.");
		System.out.println("\n************************************");
		
		return false;	
	}
	
	// Determines which employee is using the system
	// Author: Michael
	public Employee getCurrentEmployee(int inputID) {
		
		for (int i = 0; i < Store.employees.size(); i++) {
			if (inputID == Store.employees.get(i).getID()) {
				return Store.employees.get(i);
			}
		}
		return null;
	}
	
	public void checkStockLevels() {
		
		Order autoOrder = new Order();
		
		int threshold = 12;
		
		for (int i = 0; i < Store.stockItems.size(); i++) {
			
			Product currentProd = Store.stockItems.get(i).getProduct();
			if (Store.stockItems.get(i).getQty() < threshold) {
				
				OrderItem currentItem = new OrderItem((i+1), currentProd, 4, true);
				Store.orderItems.add(currentItem);
			}
		}
		autoOrder.setOrder(Store.orderItems);
		Store.orders.add(autoOrder);
	}
}
