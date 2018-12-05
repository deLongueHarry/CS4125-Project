package ui;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import transactions.*;
import customer.Customer;
import employee.*;
import retailStore.StoreFacade;
import stock.*;

public class TransactionsUI	implements UI	{
	
	private Employee emp = new Employee();
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
				Transactions newSale = createSale(findTransactionID(), StoreFacade.products, StoreFacade.stockItems);
				StoreFacade.sales.add(newSale);
				StoreFacade.ac.updateAmount(newSale.getAmount(), true);
				break;
				
			case 2:
				Transactions newReturn = createReturn(findTransactionID(), StoreFacade.sales);
				StoreFacade.returnsList.add(newReturn);
				StoreFacade.ac.updateAmount(newReturn.getAmount(), false);
				break;
				
			case 3:
				System.out.println(viewTransaction(StoreFacade.sales, StoreFacade.returnsList));
				break;
				
			default:
					System.out.println("You have entered an invalid selection. Please give your input in the form of a number between 1 and 3.");
					startInterface();
					break;
			}
		}
		
		else	{
			Transactions newSale = createSale(findTransactionID(), StoreFacade.products, StoreFacade.stockItems);
			StoreFacade.ac.updateAmount(newSale.getAmount(), true);
			StoreFacade.sales.add(newSale);
		}
		
	}

	//Generates a transactionID
	//Author: Alex
	public int findTransactionID()	{
		int transID = 1;
		for (int i = 0; i < StoreFacade.sales.size(); i++)	{
			if (transID < StoreFacade.sales.get(i).getTransID())	{
				transID = StoreFacade.sales.get(i).getTransID() + 1;
			}
		}
		
		for (int i = 0; i < StoreFacade.returnsList.size(); i++)	{
			if (transID < StoreFacade.returnsList.get(i).getTransID())	{
				transID = StoreFacade.returnsList.get(i).getTransID();
			}
		}
		
		return transID;
	}
	
	//Creates a new Sale
	//Author: Alex
	public Transactions createSale(int transID, List<Product> products, List<StockItem> stockItems)	{
		double amount = 0;
		String cardNumb = "";
		int custID = 0;
		boolean check = false;
		List<StockItem> items = new ArrayList<>();
		//Getting customer details
		Customer current = new Customer();
		while (!check)
		{
			System.out.println("Please enter customerID number");
			try
			{
				custID = Integer.parseInt(in.nextLine());
				current = new Customer(custID);
				for (int p = 0; p < StoreFacade.customers.size(); p++)	{
					if (custID == StoreFacade.customers.get(p).getCustID())	{
						current = StoreFacade.customers.get(p);
						check = true;					
					}
				}
			}
			catch (Exception e) {}
			if (!check)
			{
				System.out.println("Your input does not match a customer ID in our files, please try again");
			}
		}
		System.out.println("Logged in as: " + current.getName());
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
				if (inputArr[i].equalsIgnoreCase(products.get(j).getProductName()))	{
					for (int k = 0; k < stockItems.size(); k++) {
						if (stockItems.get(k).getProduct() == products.get(j))	{
							if (items.isEmpty())
							{
								items.add(new StockItem(items.get(items.size()).getItmID() + 1, products.get(j), stockItems.get(k).getQty(), stockItems.get(k).getUseBy()));
							}
							else
							{
								items.add(new StockItem(1, products.get(j), stockItems.get(k).getQty(), stockItems.get(k).getUseBy()));
							}
						}
					}
				}
			}
		}
		//Getting customer card numbers from their profile or adding a new one 
		if (current.getCreditCard() != "")	{
			cardNumb = current.getCreditCard();
		}
		
		
		//Checking if customer would like to use one of their vouchers
		int voucherChoice = 0;
		double discount = 0;
		if (current.getVouchers().isEmpty())	{
			check = false;
			while (!check)	{
				System.out.println("Please input the ID number of the voucher you would like to use, or 0 to skip this step.");
				for (int h = 0; h < current.getVouchers().size(); h++)	{
					System.out.println("ID:\t" + current.getVouchers().get(h).getVoucherNo() + "\nAmount:\t" + current.getVouchers().get(h).getAmount() + "\n");
				}
				try	{
					voucherChoice = Integer.parseInt(in.nextLine());
				}
				catch(Exception e)	{
					System.out.println("Please input your answer in the form of a number.");
				}
				if (voucherChoice == 0)
				{
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
				amount += Double.parseDouble(inputArr[m]) * items.get(n).getPrice() - discount;
			}
		}

		//Creates & returns a new Sales object
		TransactionsFactory fact = new TransactionsFactory();
		Transactions newSale = fact.getTransactions("sales", transID, amount, items, custID, cardNumb);
		return(newSale);
	}
	
	//Creates a new return
	//Author: Alex
	public Transactions createReturn(int transID, List<Transactions> sale)	{
		double amount = 0;
		List<StockItem> items = new ArrayList<>();
		int custID = 0;
		String cardNumb = "";
		//Gets the relevant information from the matching sale
		for (int i = 0; i < sale.size(); i++)	{
			if (transID == (sale.get(i).getTransID()))	{
				amount = sale.get(i).getAmount();
				items = sale.get(i).getItems();
				custID = sale.get(i).getCustID();
				cardNumb = sale.get(i).getCardNumb();
			}
		}
		//Returns error message if there are no sales that match
		if ((amount == 0) && (custID == 0)) {
			System.out.println("Your transaction ID does not correspond to a sale.");
			return(new Transactions(0, 0, new ArrayList<StockItem>(), 0, "0"));
		}
		//Returns the new Return
		else	{
			TransactionsFactory fact = new TransactionsFactory();
			return(fact.getTransactions("returns", transID, amount, items, custID, cardNumb));
		}
	}
	
	
	//Adds the items to the new Sale
	//Author: Alex
	public String addItems(List<Product> products, Customer current)	{
		boolean valid = false;
		String out = "";
		String choice;
		ArrayList<String> allergens =  new ArrayList<>();
		//Prints the products available for sale
		for (int h = 0; h < products.size(); h++)	{
			System.out.println(products.get(h).getProductName());
		}
		System.out.println("Please input a product from the list above. Alternately, if you are finished adding products, please enter 0.");
		choice = in.nextLine().toLowerCase();
		
		//Receives the product name or exits method when customer is finished
		if (choice.equals("0"))	{
			out = "done";
		}
		//Checks product's allergen information vs customer's allergens
		else	{
			for (int i = 0; i < products.size(); i++)	{
				if (choice.matches(products.get(i).getProductName().toLowerCase()))	{
					valid = true;			
					for (int j = 0; j < current.getAllergens().size(); j++)	{
						for (int k = 0; k < products.get(i).getAllergens().size(); k++)	{
							if (current.getAllergens().get(j).equals(products.get(i).getAllergens().get(k)))	{
								allergens.add(current.getAllergens().get(j));
							}
						}
					}
				}
			}
		}
		
		
		//Gives customer option of not buying items if they are allergic to them
		String yesNo = "y";
		if (!(out.equals("done")) && valid)	{
			if (!(allergens.isEmpty()))
			{
				System.out.println("Are you sure you wish to purchase " + choice + "? It contains these items from your list of allergens:");
				for (int q = 0; q < allergens.size(); q++)	{
					System.out.println(allergens.get(q));
				}
				System.out.println("Please enter y/n");
				yesNo = in.nextLine().toLowerCase();
			}
			
			if (yesNo.equals("y"))	{
				out += choice + ",";
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

	//Lets employee view details of a transaction
	//Author: Alex
	public String viewTransaction(List<Transactions> sale, List<Transactions> returnsList)	{
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
				
				List<StockItem> s = sale.get(i).getItems();
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
				
				List<StockItem> r = returnsList.get(i).getItems();
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