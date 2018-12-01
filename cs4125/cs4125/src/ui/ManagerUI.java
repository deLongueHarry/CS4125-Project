package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cs4125.Store;
import customer.Customer;
import employee.Employee;
import transactions.Voucher;

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
		
		System.out.printf("\n\nSelect an option from the following:\n %3s\n %3s\n %3s\n\nEnter selection: ",
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
					
					List<String> allergens = new ArrayList<>();
					boolean done = false;
					while (!done)	{
						System.out.println("Please enter one of your allergens. If you are done, please enter 0");
						String allergen = in.nextLine();
						if (allergen.equals("0"))	{
							done = true;
						}
						else	{
							allergens.add(allergen);
						}
						
					}
					System.out.print("\nCredit card no: ");
					List<Voucher> empty = new ArrayList<>();
					String creditCard = in.nextLine();
					Store.customers.add(new Customer(custID, 0, custName, creditCard, allergens, empty));
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
	
	private void ViewRequests() {
		
	}
	
	//Allows adding, removing and viewing employees
	//Author: Alex
	private void UpdateEmployees() {
		boolean valid = false;
		int choice = 0;
		
		while(!valid)	{
			System.out.println("Please choose from the following:\n(1)Add new employee\n(2)Remove existing employee\n(3)View employee information");
			try	{
				choice = Integer.parseInt(in.nextLine());
			}
			catch (NumberFormatException e)	{}
			
			switch(choice)	{
			
			//Add new employee
			case 1:
				boolean isValid = false;
				
				int ID = 0;
				if (Store.employees.isEmpty())	{
					ID = 1;
				}
				else	{
					for (int i = 0; i < Store.employees.size(); i++)	{
						if (Store.employees.get(i).getID() > ID)	{
							ID = Store.employees.get(i).getID() + 1;
						}
					}
				}
				
				String type = "";
				while (!isValid)	{
					System.out.println("Please enter the type of employee you'd like to add:\n(1)Manager\n(2)Stock Employee");
					type = in.nextLine().toLowerCase();
					if (!(type.equals("manager") || !(type.equals("stock employee"))))	{
						System.out.println("Your input is invalid, please try again.");
					}
					else
						isValid = true;
				}
				
				System.out.println("Please enter the employee's name:");
				String name = in.nextLine();
				
				isValid = false;
				String password = "";
				while (!isValid)	{
					System.out.println("Please enter the new employee's password");
					password = in.nextLine();
					System.out.println("Please re-enter the password");
					String checkPassword = in.nextLine();
					if (password.equals(checkPassword))	{
						isValid = true;
					}
					else
						System.out.println("Your passwords do not match, please try again.");
				}
				
				System.out.println("Please enter the employee's address");
				String address = in.nextLine();
				
				System.out.println("Please enter the employee's phone number");
				String phoneNo = in.nextLine();
				
				Store.employees.add(new Employee(ID, type, name, password, address, phoneNo));
				
				valid = true;
				break;
				
			//Remove existing employee from file
			case 2:
				isValid = false;
				while (!isValid)
				{
					int id = 0;
					System.out.println("Please enter the ID number of the employee you'd like to remove");
					try	{
						id = Integer.parseInt(in.nextLine());
					}
					catch(NumberFormatException e) {}
					for (int i = 0; i < Store.employees.size(); i++)	{
						if (id == Store.employees.get(i).getID())	{
							Store.employees.remove(i);
							System.out.println("Employee successfully removed.");
							isValid = true;
						}
						else	{
							System.out.println("You have entered an invalid ID. Please try again.");
						}
					}
				}
				valid = true;
				break;
				
				
			//View employee info	
			case 3:
				isValid = false;
				while (!isValid)	{
					int id = 0;
					System.out.println("Please enter the ID number of the employee you'd like to view");
					try	{
						id = Integer.parseInt(in.nextLine());
					}
					catch(NumberFormatException e) {}
					for (int i = 0; i < Store.employees.size(); i++)	{
						if (id == Store.employees.get(i).getID())	{
							System.out.println("ID:\t" + Store.employees.get(i).getID());
							System.out.println("Type:\t" + Store.employees.get(i).getType());
							System.out.println("Name:\t" + Store.employees.get(i).getName());
							System.out.println("Address:\t" + Store.employees.get(i).getAddress());
							System.out.println("Phone Number:\t" + Store.employees.get(i).getPhoneNo());
							isValid = true;
						}
						else if (i + 1 == Store.employees.size())	{
							System.out.println("You have entered an invalid ID. Please try again.");
						}
					}
				}
				valid = true;
				break;
				
			default:
				System.out.println("Invalid input. Please ensure your input is a number between 1 and 3.");
				break;
			}
		}		
	}
}
