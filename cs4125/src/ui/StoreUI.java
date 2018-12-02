package ui;

import java.util.Scanner;

// Author: Michael
public class StoreUI implements UI {

	private Scanner in = new Scanner(System.in);
	public void startInterface() {
		
		String error1 = "\nInvalid input. Please enter 1 for customer, 2 for employee, or 0 to quit the program: ";
		System.out.print("Input 1 for customer, 2 for employee, 0 to quit: ");
		
		boolean validSelection = false;
		while(!validSelection) {
			try {

				int input1 = Integer.parseInt(in.nextLine());
				if (input1 == 1) {
					
					validSelection = true;
					UI customerUI = new CustomerUI();
					customerUI.startInterface();
				}
				else if (input1 == 2) {
					
					validSelection = true;
					UI login = new LoginUI();
					login.startInterface();
					//LoginUI();
				}
				else if (input1 == 0) {
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
}
