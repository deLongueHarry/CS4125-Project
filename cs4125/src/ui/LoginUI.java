package ui;

import employee.*;
import retailStore.StoreFacade;

import java.util.List;
import java.util.Scanner;

// Author: Michael
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
	public Employee getCurrentEmployee(int inputID) {
		
		for (int i = 0; i < StoreFacade.employees.size(); i++) {
			if (inputID == StoreFacade.employees.get(i).getID()) {
				return StoreFacade.employees.get(i);
			}
		}
		return null;
	}
}
