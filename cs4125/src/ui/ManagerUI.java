package ui;

import java.util.Scanner;

public class ManagerUI implements UI {

	public Scanner in = new Scanner(System.in);
	
	@Override
	public void startInterface() {
		boolean valid = false;
		int choice = 0;
		while(!valid)	{
			System.out.println("Select an option from the following list:\n(1)Stock menu\n(2)Transaction Menu\n(0)Exit");
			try	{
				choice = Integer.parseInt(in.nextLine());
				valid = true;
			}
			catch (Exception e)	{
				System.out.println("Please give your input in the form of a number.");
			}
		}
		
		switch(choice)	{
		case 1:
			//Implement stock menu
			break;
			
		case 2:
			TransactionsUI transactions = new TransactionsUI();
			transactions.emp.setID(1);
			transactions.startInterface();
			break;
			
		default:
			break;
		}
	}
}
