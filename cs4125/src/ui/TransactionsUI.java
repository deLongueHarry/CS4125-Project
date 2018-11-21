package ui;

import java.util.ArrayList;
import java.util.Scanner;
import goods.*;
import transactions.*;
import cs4125.*;
import customer.Customer;
import employee.*;

public class TransactionsUI	implements UI{
	
	public Manager emp;
	
	private Scanner in = new Scanner(System.in);
	
	//Begins UI
	//Author: Alex
	public void startInterface()	{
		if (emp.getID() > 0) {
			int choice = 0;
			//Receives choice from user
			try	{
				System.out.println("Please choose a number:\n(1) Create new sale\n(2) Create new return\n(3) View a transaction\n(0) Exit");
				choice = Integer.parseInt(in.nextLine());
			}
			catch(NumberFormatException e) {
				choice = 0;
			}
			//Returns user's choice, or error message.
			switch(choice) {
			
			case 0:
				break;
			case 1:
				Transactions newSale = createSale(findTransactionID(), Store.products, Store.stockItems);
				Store.sales.add(newSale);
				Store.ac.updateAmount(newSale.getAmount(), true);
				break;
				
			case 2:
				Transactions newReturn = createReturn(findTransactionID(), Store.sales, Store.stockItems);
				Store.returnsList.add(newReturn);
				Store.ac.updateAmount(newReturn.getAmount(), false);
				break;
				
			case 3:
				System.out.println(viewTransaction(Store.sales, Store.returnsList, Store.products));
				break;
				
			default:
					System.out.println("You have entered an invalid selection. Please give your input in the form of a number between 1 and 3.");
					startInterface();
					break;
			}
		}
		
		else	{
			Transactions newSale = createSale(findTransactionID(), Store.products, Store.stockItems);
			Store.sales.add(newSale);
			Store.ac.updateAmount(newSale.getAmount(), true);
		}
		
	}

	//Generates a transactionID
	//Author: Alex
	public int findTransactionID()	{
		int transID = 0;
		for (int i = 0; i < Store.sales.size(); i++)	{
			if (transID < Store.sales.get(i).getTransID())	{
				transID = Store.sales.get(i).getTransID();
			}
		}
		
		for (int i = 0; i < Store.returnsList.size(); i++)	{
			if (transID < Store.returnsList.get(i).getTransID())	{
				transID = Store.returnsList.get(i).getTransID();
			}
		}
		
		return transID;
	}
	
	//Creates a new Sale
	//Author: Alex
	public Transactions createSale(int transID, ArrayList<Product> products, ArrayList<StockItem> stockItems)	{
		//Sales newSale;
		double amount = 0;
		String cardNumb = "";
		int custID = 0;
		boolean check = false;
		ArrayList<StockItem> items = new ArrayList<StockItem>();
		//Getting customer details
		while (!check)
		{
			System.out.println("Please enter customerID number");
			try
			{
				custID = Integer.parseInt(in.nextLine());
				check = true;
			}
			catch (Exception e)
			{
				System.out.println("Please give your input in the form of a number");
			}
		}
		Customer current = new Customer(custID);
		for (int p = 0; p < Store.customers.size(); p++)	{
			if (custID == Store.customers.get(p).getCustID())	{
				current = Store.customers.get(p);
			}
		}
		
		check = false;
		String input = "";
		//Receives the items being added to the Sale from the addItems() method
		while(!check)	{
			
			String checker = addItems(products, current);
			if (!(checker.equals("done")))	{
				input += checker;
			}
			else
				check = true;
			
		}
		//Uses input to add the items to the ArrayList of StockItems
		String[] inputArr = input.split(",");
		for (int i = 0; i < inputArr.length; i+=2) {
			for (int j = 0; j < products.size(); j++)	{
				if (inputArr[i].equals(products.get(j).getProductName()))	{
					for (int k = 0; k < stockItems.size(); k++) {
						if (stockItems.get(k).getProduct() == products.get(j))	{
							items.add(new StockItem(items.get(items.size()-1).getItmID(), products.get(j), stockItems.get(k).getQty(), stockItems.get(k).getUseBy()));
						}
					}
				}
			}
		}
		//Getting customer card numbers from their profie or adding a new one 
		check = false;
		if (current.getCreditCard() != "")	{
			cardNumb = current.getCreditCard();
		}
		else	{
					System.out.println("Please enter your credit card number now:");
					cardNumb = in.nextLine();
		}
		
		int voucherChoice = 0;
		double discount = 0;
		if (!(current.getVouchers().size() == 0))	{
			check = false;
			while (!check)	{
				System.out.println("Please input the ID number of the voucher you would like to use, or 0 to skip this step.");
				for (int h = 0; h < current.getVouchers().size(); h++)	{
					System.out.println("ID:\t" + current.getVouchers().get(h).getAmount());
					System.out.println("Amount:\t" + current.getVouchers().get(h).getAmount() + "\n");
				}
				try	{
					voucherChoice = Integer.parseInt(in.nextLine());
				}
				catch(Exception e)	{
					System.out.println("Please input your answer in the form of a number.");
				}
				if (voucherChoice == 0)
				{
					System.out.println("Step skipped.");
					check = true;
				}
				else
				{
					for (int w = 0; w < current.getVouchers().size(); w++)	{
						if (voucherChoice == current.getVouchers().get(w).getAmount())	{
							discount = current.getVouchers().get(w).getAmount();
							System.out.println("Voucher applied!");
							check = true;
						}
					}
				}
			}
		}
		
		//Gets the total price for the sale
		for (int m = 1; m < inputArr.length; m+=2)	{
			for (int n = 0; n < items.size(); n++)		{
				amount += Integer.parseInt(inputArr[m]) * items.get(n).getPrice() - discount;
			}
		}

		//Creates & returns a new Sales object
		TransactionsFactory fact = new TransactionsFactory();
		Transactions newSale = fact.getTransactions("sale", transID, amount, items, custID, cardNumb);
		return(newSale);
	}
	
	//Creates a new return
	//Author: Alex
	public Transactions createReturn(int transID, ArrayList<Transactions> sale, ArrayList<StockItem> stockItems)	{
		double amount = 0;
		ArrayList<StockItem> items = new ArrayList<StockItem>();
		int custID = 0;
		String cardNumb = "";
		//Gets the relevant information from the matching sale
		for (int i = 0; i < sale.size(); i++)	{
			if (transID == (sale.get(i).getTransID()))	{
				amount = sale.get(i).getAmount();
				stockItems = sale.get(i).getItems();
				custID = sale.get(i).getCustID();
				cardNumb = sale.get(i).getCardNumb();
			}
		}
		//Returns error message if there are no sales that match
		if ((amount == 0) && (custID == 0)) {
			System.out.println("Your transaction ID does not correspond to a sale.");
			Transactions newReturn = new Transactions(0, 0, new ArrayList<StockItem>(), 0, "0");
			return newReturn;
		}
		//Returns the new Return
		else	{
			TransactionsFactory fact = new TransactionsFactory();
			Transactions newReturn = fact.getTransactions("return", transID, amount, items, custID, cardNumb);
			return newReturn;
		}
	}
	
	//Adds the items to the new Sale
	//Author: Alex
	public String addItems(ArrayList<Product> products, Customer current)	{
		boolean valid = false;
		String out = "";
		String choice;
		ArrayList<String> allergens =  new ArrayList<String>();
		//Prints the products available for sale
		for (int h = 0; h < products.size(); h++)	{
			System.out.println(products.get(h).getProductName());
		}
		System.out.println("Please input a product from the list above. Alternately, if you are finished adding products, please enter 0.");
		choice = in.nextLine().toLowerCase();
		//Receives the product name or exist method when customer is finished
		switch(choice) {
			case "0":
				out = "done";
				break;
				
			default:
				for (int i = 0; i < products.size(); i++)	{
					if (choice.matches(products.get(i).getProductName().toLowerCase()))	{
						valid = true;
						if (current.getAllergens().size() > 0)	{
							for (int j = 0; j < current.getAllergens().size(); j++)	{
								for (int k = 0; k < products.get(i).getAllergens().size(); k++)	{
									if (current.getAllergens().get(j) == products.get(i).getAllergens().get(k))	{
										allergens.add(current.getAllergens().get(j).toString());
									}
								}
							}
						}
					}
				}
				break;
		}
		//Adds new entry to return
		if (!(out.equals("done")) && valid)	{
			System.out.println("Are you sure you wish to purchase " + choice + "? It contains these items from your list of allergens:");
			for (int q = 0; q < allergens.size(); q++)	{
				System.out.println(allergens.get(q));
			}
			System.out.println("Please enter y/n");
			String yesNo = in.nextLine().toLowerCase();
			if (yesNo.equals("y"))	{
				out += "," + choice + ",";
				try	{
					System.out.println("Please enter amount of this item in your transaction.");
					out += in.nextLine();
				}
				catch(NumberFormatException e) {
					System.out.println("Please give your input in the form of a number.");
				}
			}
			else	{
				System.out.println(choice + " was not added to sale.");
			}
		}
		//Returns error message if invalid input received as product name
		else if (!(out.equals("done")) && !(valid))	{
			System.out.println("Your input did not match a product. Please try again");
		}
		return out;
	}

	//Lets user view a transaction
	//Author: Alex
	public String viewTransaction(ArrayList<Transactions> sale, ArrayList<Transactions> returnsList, ArrayList<Product> products)	{
		System.out.println("Please input the TransactionID of the transaction you wish to view.");
		int transID = Integer.parseInt(in.nextLine());
		int count = 1;
		//Finds sale that matches the transaction number
		for (int i = 0; i < sale.size(); i++)	{
			if(sale.get(i).getTransID() == transID) {
				//Prints out transaction information
				System.out.println("Transaction ID:\t" + transID);
				System.out.println("Amount:\t" + sale.get(i).getAmount());
				System.out.println("Customer ID:\t" + sale.get(i).getCustID());
				
				ArrayList<StockItem> s = sale.get(i).getItems();
				for (int j = 0; j < s.size(); j++)	{
					System.out.println("Item " + count + ":");
					System.out.println("Product ID:\t" + s.get(j).getProduct().getProductID()); //company, price, useBy
					System.out.println("Product Name:\t" + s.get(j).getProduct().getProductName());
					System.out.println("Product Type:\t" + s.get(j).getProduct().getType());
					System.out.println("Company:\t" + s.get(j).getProduct().getCompany());
					System.out.println("Price:\t" + s.get(j).getPrice());
				}
				return ("Transaction successfully viewed.");
			}
			//If no sale that matches, finds return that matches
			else if(returnsList.get(i).getTransID() == transID)	{
				//Prints out transaction information
				System.out.println("Transaction ID:\t" + transID);
				System.out.println("Amount:\t" + returnsList.get(i).getAmount());
				System.out.println("Customer ID:\t" + returnsList.get(i).getCustID());
				
				ArrayList<StockItem> r = returnsList.get(i).getItems();
				for (int j = 0; j < r.size(); j++)	{
					System.out.println("Item " + count + ":");
					System.out.println("Product ID:\t" + r.get(j).getProduct().getProductID()); //company, price, useBy
					System.out.println("Product Name:\t" + r.get(j).getProduct().getProductName());
					System.out.println("Product Type:\t" + r.get(j).getProduct().getType());
					System.out.println("Company:\t" + r.get(j).getProduct().getCompany());
					System.out.println("Price:\t" + r.get(j).getPrice());
				}
				return ("Return successfully viewed.");
			}
			//Prints out error message
			else	{
				System.out.println("Your transaction ID did not match any transactions.");
			}
		}
		return "";
	}
}