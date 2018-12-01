package cs4125;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import employee.*;
import goods.*;
import ui.*;
import customer.Customer;
import account.Account;
import transactions.*;
import dataPersistence.*;


public class Store {
	
	public static ArrayList<Employee> employees = new ArrayList<Employee>();
	public static ArrayList<Customer> customers = new ArrayList<Customer>();
	public static ArrayList<Product> products = new ArrayList<Product>();
	public static ArrayList<Order> orders = new ArrayList<Order>();
	public static ArrayList<StockItem> stockItems = new ArrayList<StockItem>();
	public static ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
	public static ArrayList<Transactions> sales = new ArrayList<>();
	public static ArrayList<Transactions> returnsList = new ArrayList<>();
	public static Account ac = new Account(0);
		
	public static void main(String[] args) throws IOException {
		
		loadFromArrayLists();
		checkStockLevels();
		
		
		System.out.println("- Login info -\nFor testing stockEmp code: ID, Password = 12");
		System.out.println("For testing manager code:  ID, Password = 13\n");
		
		UI store = new StoreUI();
		store.startInterface();
		
		writeToFiles();
	}
	
	// Author: Michael 
	public static void loadFromArrayLists() throws FileNotFoundException, IOException {
		
		Product temp = null;	
	
		// Loads employees into the system from csv file
		File empFile = new File("employees.txt");
		Scanner empScanner = new Scanner(empFile);		

		while(empScanner.hasNext()) {
			
			String[] empStr = empScanner.nextLine().split(",");
			Employee tempEmp;
			
			String empType = empStr[0].substring(0, 1);
			empStr[0] = empStr[0].substring(1);
			
			if (empType.equalsIgnoreCase("m"))
				tempEmp = new Manager();
			else if (empType.equalsIgnoreCase("s"))
				tempEmp = new StockEmployee();
			else
				break;
			
			tempEmp.setID(Integer.parseInt(empStr[0]));
			tempEmp.setName(empStr[1]);
			tempEmp.setPassword(empStr[2]);
			tempEmp.setAddress(empStr[3]);
			tempEmp.setPhoneNo(empStr[4]);
			
			employees.add(tempEmp);
		}
		empScanner.close();
				
		
		// Loads customers into the system from csv file
		File custFile = new File("customers.txt");
		Scanner custScanner = new Scanner(custFile);		

		while(custScanner.hasNext()) {
			
			ArrayList<String> allergenList = new ArrayList<String>();
			ArrayList<Voucher> vouchers = new ArrayList<>();
			String[] custStr = custScanner.nextLine().split(",");
			
			try {
				if (custStr[4].equals("")) {
					allergenList = null;
				}
				else {
					String[] allergens = custStr[4].split("/");
					for (int i = 0; i < allergens.length; i++) {
						allergenList.add(allergens[i]);
					}			
				}						
			} 
			catch (ArrayIndexOutOfBoundsException a) {
			}
			
			if(custStr.length > 5)
			{	
				Voucher tempVouch;
				String[] splitVouch = custStr[5].split("/");

				for (int i = 0; i < splitVouch.length; i++)
				{
					String[] voucherInfo = splitVouch[i].split(":");
					tempVouch = new Voucher(Integer.parseInt(voucherInfo[0]), Double.parseDouble(voucherInfo[1]));
					vouchers.add(tempVouch);
				}				
			}
			
			Customer tempCust = new Customer(Integer.parseInt(custStr[0]), Integer.parseInt(custStr[1]), 
												custStr[2], custStr[3], allergenList, vouchers);
			customers.add(tempCust);
			
		}
		custScanner.close();
		
		// Loads products into the system from csv file
		File prodFile = new File("products.txt");
		Scanner prodScanner = new Scanner(prodFile);	
		
		
		while(prodScanner.hasNext()) {
			
			ArrayList<String> allergenList = new ArrayList<String>();
			String[] prodStr = prodScanner.nextLine().split(",");	
			try {
				
				if (prodStr[6].equals("")) {
					allergenList = null;
				}
				else {
					String[] allergens = prodStr[6].split("/");
					for (int i = 0; i < allergens.length; i++) {
						allergenList.add(allergens[i]);
					}			
				}						
			} 
			catch (ArrayIndexOutOfBoundsException a) {
			}
			
			Product tempProd = new Product(Integer.parseInt(prodStr[0]), prodStr[1], prodStr[2], 
											prodStr[3], Double.parseDouble(prodStr[4]), 
												Integer.parseInt(prodStr[5]), allergenList);
			
			products.add(tempProd);
		}
		prodScanner.close();
		
		
		// Loads stockItems into the system from csv file
		File stockItmFile = new File("stockItems.txt");
		Scanner stockItmScanner = new Scanner(stockItmFile);		
		while(stockItmScanner.hasNext()) {
			
			String[] stockItmStr = stockItmScanner.nextLine().split(",");
			int prodID = Integer.parseInt(stockItmStr[1]);

			for (int i = 0; i < products.size(); i++) {
				if(prodID == products.get(i).getProductID()) {
					temp = products.get(i);
				}
			}
			//int stockItmID, Product prod, int qty, String useBy
			StockItem tempStockItm = new StockItem(Integer.parseInt(stockItmStr[0]), temp, 
													Integer.parseInt(stockItmStr[2]), stockItmStr[4]);
			stockItems.add(tempStockItm);
		}
		stockItmScanner.close();	
		
		
		// Loads orderItems into the system from csv file
		File orderItmFile = new File("orderItems.txt");
		Scanner orderItmScanner = new Scanner(orderItmFile);
		while(orderItmScanner.hasNext()) {
			
			String[] orderItmStr = orderItmScanner.nextLine().split(",");
			int prodID = Integer.parseInt(orderItmStr[1]);

			for (int i = 0; i < products.size(); i++) {
				if(prodID == products.get(i).getProductID()) {
					temp = products.get(i);
				}
			}
			OrderItem tempOrderItm = new OrderItem(Integer.parseInt(orderItmStr[0]), temp, Integer.parseInt(orderItmStr[2]));
			orderItems.add(tempOrderItm);
		}
		orderItmScanner.close();
		
		
		// Loads orders into the system from csv file
		File orderFile = new File("orders.txt");
		Scanner orderScanner = new Scanner(orderFile);
		
		while(orderScanner.hasNext()) {
			String[] orderStr = orderScanner.nextLine().split(",");
			

			boolean approved, paid;
			ArrayList<OrderItem> items = new ArrayList<>();
			
			if (orderStr[1] != null) {
				String[] orderItemIds = orderStr[1].split("/");
				for (int i = 0; i < orderItemIds.length; i++)	{

					int idToCompare = Integer.parseInt(orderItemIds[i]);
					for (int j = 0; j < orderItems.size(); j++)	{
						
						if (idToCompare == orderItems.get(j).getItmID())	{
							items.add(orderItems.get(j));
						}
					}
				}
			}

			approved = (orderStr[4].equalsIgnoreCase("true")) ? true : false;	
			paid = (orderStr[5].equalsIgnoreCase("true")) ? true : false;		
				
			for (int i = 0; i < employees.size(); i++)	{
				if (Integer.parseInt(orderStr[3]) == employees.get(i).getID())	{
					
					Order tempOrd = new Order(Integer.parseInt(orderStr[0]), items, orderStr[2], employees.get(i), approved, paid);
					tempOrd.setDateOrdered(orderStr[6]);
					orders.add(tempOrd);
				}
			}
		}
		orderScanner.close();
	}
	
	// Checks if stock levels are below a certain threshold, and generates orders if so
	// Author: Michael
	public static void checkStockLevels() {
		
		Order todaysOrder = new Order();
		int threshold = 12;
		for (int i = 0; i < stockItems.size(); i++) {
			
			Product currentProd = stockItems.get(i).getProduct();
			if (stockItems.get(i).getQty() < threshold) {
				
				OrderItem currentItem = new OrderItem((i+1), currentProd, 4);
				orderItems.add(currentItem);
			}
		}
		// System generated orders are approved automatically
		todaysOrder.Approve();
		orders.add(todaysOrder);
	}
	
	//Writes ArrayList Data to files
	//Author: Alex	
	public static void writeToFiles() throws IOException, FileNotFoundException{
		
		DataPersistence d = new DataPersistence();
		d.employeesToFile(employees);
		d.customersToFile(customers);
		d.productsToFile(products);
		d.ordersToFile(orders);
		d.stockItemsToFile(stockItems);
		d.orderItemsToFile(orderItems);
		d.salesToFile(sales);
		d.returnsToFile(returnsList);
		d.accountToFile(ac);
		
	}

}
