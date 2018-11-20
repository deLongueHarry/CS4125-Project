package cs4125;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import employee.*;
import goods.*;
import ui.*;

public class Store {
	
	public static ArrayList<Employee> employees = new ArrayList<Employee>();
	//public static ArrayList<Customer> customers = new ArrayList<Customer>();
	public static ArrayList<Product> products = new ArrayList<Product>();
	public static ArrayList<Order> orders = new ArrayList<Order>();
	public static ArrayList<StockItem> stockItems = new ArrayList<StockItem>();
	public static ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
		
	public static void main(String[] args) throws IOException {
				
		
		loadFromArrayLists();
		checkStockLevels();

		System.out.println("- Login info -\nFor testing stockEmp code: ID, Password = 12");
		System.out.println("For testing manager code:  ID, Password = 13\n");
		
		UI store = new StoreUI();
		store.startInterface();
		
		//writeToFiles();
	}
	
	public static void loadFromArrayLists() throws FileNotFoundException, IOException {
		
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
		
		
		
		/*File custFile = new File("customers.txt");
		Scanner custScanner = new Scanner(custFile);	
		while(custScanner.hasNext()) {
			
			String[] custStr = custScanner.nextLine().split(",");
			Customer tempCust = new Customer(......);
			
			customers.add(tempCust);
		}
		custScanner.close();*/
		
		
		
		File prodFile = new File("products.txt");
		Scanner prodScanner = new Scanner(prodFile);	
		while(prodScanner.hasNext()) {
			
			String[] prodStr = prodScanner.nextLine().split(",");
			Product tempProd = new Product(Integer.parseInt(prodStr[0]), prodStr[1], prodStr[2], 
											Double.parseDouble(prodStr[3]), prodStr[4], Integer.parseInt(prodStr[5]));
			
			products.add(tempProd);
		}
		prodScanner.close();
		
		
		
		/*File orderFile = new File("orders.txt");
		Scanner orderScanner = new Scanner(orderFile);
		
		while(orderScanner.hasNext()) {
			
			String[] orderStr = orderScanner.nextLine().split(",");
			Product tempOrd = new Order();
			
			orders.add(tempOrd);
		}
		orderScanner.close();*/
		
		
		
		Product temp = null;	
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
			StockItem tempStockItm = new StockItem(Integer.parseInt(stockItmStr[0]), temp, Integer.parseInt(stockItmStr[2]), stockItmStr[3]);
			stockItems.add(tempStockItm);
		}
		stockItmScanner.close();
		
		
		
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
	}
	
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
		orders.add(todaysOrder);
	}
	
	//public static void writeToFiles() {}
}
