package ui;

import employee.*;
import goods.Order;
import goods.OrderItem;
import goods.Product;
import cs4125.StoreFacade;

import java.util.List;
import java.util.Scanner;

public class LoginUI implements UI {
	
	public static Employee emp;
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
			catch (NumberFormatException e) {}
			
			System.out.print("Password: ");
			String inputPass = in.nextLine();	
			
			validLogin = checkValidLogin(inputID, inputPass);
			emp = getCurrentEmployee(inputID);
			
			if (validLogin) {
				
				UI currentUI = null;
				
				System.out.printf("\n\n************* %d *************\n\nLogged in as: %s", emp.getID(), emp.getName());
				
				Criteria crit = new CriteriaManager();
				List<Employee> managers = crit.meetCriteria(StoreFacade.employees);
				crit = new CriteriaStockEmployee();
				List<Employee> stockEmps = crit.meetCriteria(StoreFacade.employees);
				
				for (int i = 0; i < managers.size(); i++)
				{
					if (managers.get(i) == emp)
					{
						currentUI = new ManagerUI();
					}
					else
					{
						for (int j = 0; j < stockEmps.size(); j++)
						{
							if (stockEmps.get(j) == emp)
							{
								currentUI = new StockEmployeeUI();
							}
						}
					}
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

		for (int i = 0; i < StoreFacade.employees.size(); i++) {
			if (inputID == StoreFacade.employees.get(i).getID() && inputPass.equals(StoreFacade.employees.get(i).getPassword())) {
				return true;
			}
		}
		
		System.out.println("\nInvalid username and/or password. Please try again.");
		System.out.println("\n************************************");
		
		return false;	
	}
	
	// Determines which employee is using the system
	// Author: Michael
	public Employee getCurrentEmployee(int inputID) {
		
		for (int i = 0; i < StoreFacade.employees.size(); i++) {
			if (inputID == StoreFacade.employees.get(i).getID()) {
				return StoreFacade.employees.get(i);
			}
		}
		return null;
	}
	
	public void checkStockLevels() {
		
		Order autoOrder = new Order();
		
		int threshold = 12;
		
		for (int i = 0; i < StoreFacade.stockItems.size(); i++) {
			
			Product currentProd = StoreFacade.stockItems.get(i).getProduct();
			if (StoreFacade.stockItems.get(i).getQty() < threshold) {
				
				OrderItem currentItem = new OrderItem((i+1), currentProd, 4);
				StoreFacade.orderItems.add(currentItem);
			}
		}
		autoOrder.setOrderItems(StoreFacade.orderItems);
		StoreFacade.orders.add(autoOrder);
	}
}
