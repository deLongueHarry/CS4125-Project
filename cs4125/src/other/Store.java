package other;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;

import employee.*;
import goods.*;
import ui.*;
import customer.Customer;
import account.Account;
import transactions.*;

public class Store {
	
	public static ArrayList<Employee> employees = new ArrayList<>();
	public static ArrayList<Customer> customers = new ArrayList<>();
	public static ArrayList<Product> products = new ArrayList<>();
	public static ArrayList<Order> orders = new ArrayList<>();
	public static ArrayList<StockItem> stockItems = new ArrayList<>();
	public static ArrayList<OrderItem> orderItems = new ArrayList<>();
	public static ArrayList<Sales> sales = new ArrayList<>();
	public static ArrayList<Returns> returnsList = new ArrayList<>();
	public static Account ac;
	
	public static void main(String[] args) throws IOException {
				
		
		loadFromArrayLists();
		checkStockLevels();

		System.out.println("- Login info -\nFor testing stockEmp code: ID, Password = 12");
		System.out.println("For testing manager code:  ID, Password = 13\n");
		
		UI store = new StoreUI();
		store.startInterface();
		
		writeToFiles();
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
		
		File prodFile = new File("products.txt");
		Scanner prodScanner = new Scanner(prodFile);	
		while(prodScanner.hasNext()) {
			
			String[] prodStr = prodScanner.nextLine().split(",");
			ArrayList<String> allergens = new ArrayList<>();
			for (int i = 0; i < prodStr.length - 5; i++)	{
				allergens.add(prodStr[i + 5]);
			}
			Product tempProd = new Product(Integer.parseInt(prodStr[0]), Integer.parseInt(prodStr[1]), Double.parseDouble(prodStr[2]), prodStr[3], prodStr[4], prodStr[5], allergens);
			
			products.add(tempProd);
		}
		prodScanner.close();		
		
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
			StockItem tempStockItm = new StockItem(Integer.parseInt(stockItmStr[0]), temp, Integer.parseInt(stockItmStr[2]), Double.parseDouble(stockItmStr[3]), stockItmStr[4]);
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
			OrderItem tempOrderItm = new OrderItem(Integer.parseInt(orderItmStr[0]), temp, Integer.parseInt(orderItmStr[2]), Double.parseDouble(orderItmStr[3]));
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
				OrderItem currentItem = new OrderItem((i+1), currentProd, 4, (4*currentProd.getCostPrice()));
				orderItems.add(currentItem);
			}
		}
		orders.add(todaysOrder);
	}
	//Writes ArrayList Data to files
	//Author: Alex
	public static void writeToFiles() throws IOException{
		
		//Writing employees to file
		File emp = new File("employees.txt");
		String eachLine = "";
		int i = 0;
		int j = 0;
		for (; i < employees.size(); i++)	{
			eachLine += Integer.toString(employees.get(i).getID()) + ",";
			eachLine += employees.get(i).getName() + ",";
			eachLine += employees.get(i).getPassword() + ",";
			eachLine += employees.get(i).getAddress() + ",";
			eachLine += employees.get(i).getPhoneNo() + ",\n";
		}
		BufferedWriter empWriter = new BufferedWriter(new FileWriter(emp));
		empWriter.write(eachLine);
		empWriter.close();
		
		//Writing customers to file
		eachLine = "";
		File cust = new File("customers.txt");
		i = 0;
		for (; i < customers.size(); i++)	{
			eachLine += Integer.toString(customers.get(i).getCustID()) + ",";
			for (; j < customers.get(i).getCreditCards().size(); j++)	{
				eachLine += Integer.toString(customers.get(i).getCreditCards().get(j)) + ",";
			}
			j = 0;
			for (; j < customers.get(i).getAllergens().size(); j++)	{
				eachLine += customers.get(i).getAllergens().get(j) + ",";
			}
			j = 0;
			for (; j < customers.get(i).getVouchers().size(); j++)	{
				eachLine += Double.toString(customers.get(i).getVouchers().get(j).getAmount()) + ",";
				eachLine += Integer.toString(customers.get(i).getVouchers().get(j).getVoucherNo()) + ",";
			}
			j = 0;
		}
		BufferedWriter custWriter = new BufferedWriter(new FileWriter(cust));
		custWriter.write(eachLine);
		custWriter.close();
			
		//Writing products to file
		eachLine = "";
		File prod = new File("products.txt");
		i = 0;
		for (; i < products.size(); i++)	{
			eachLine += Integer.toString(products.get(i).getProductID()) + ",";
			eachLine += Integer.toString(products.get(i).getMinimumOrder()) + ",";
			eachLine += Double.toString(products.get(i).getCostPrice()) + ",";
			eachLine += products.get(i).getProductName() + ",";
			eachLine += products.get(i).getType() + ",";
			eachLine += products.get(i).getCompany() + ",";
			
			for (; j < products.get(i).getAllergens().size(); j++)	{
				eachLine += products.get(i).getAllergens().get(j) + ",";
			}
			j = 0;
		}
		BufferedWriter prodWriter = new BufferedWriter(new FileWriter(prod));
		prodWriter.write(eachLine);
		prodWriter.close();
		
		//Writing orders to file
		eachLine = "";
		File ord = new File("orders.txt");
		i = 0;
		for (; i < orders.size(); i++)	{
			eachLine += Integer.toString(orders.get(i).getOrderID()) + ",";
			
			for (; j < orders.get(i).getOrder().size(); j++)	{
				eachLine += Integer.toString(orders.get(i).getOrder().get(j).getOrderItmID()) + ",";
			}
			j = 0;
			eachLine += orders.get(i).getDateOrdered() + ",";
			eachLine += Integer.toString(orders.get(i).getEmp().getID()) + ",";
			if (orders.get(i).getPaid())
				eachLine += "true,";
			else
				eachLine += "false";
		}
		BufferedWriter ordWriter = new BufferedWriter(new FileWriter(ord));
		ordWriter.write(eachLine);
		ordWriter.close();
		
		//Writing stock items to file
		eachLine = "";
		File stock = new File("stockitems.txt");
		i = 0;
		
		for (; i < stockItems.size(); i++)	{
			eachLine += Integer.toString(stockItems.get(i).getStockItmID()) + ",";
			eachLine += Integer.toString(stockItems.get(i).getProduct().getProductID()) + ",";
			eachLine += Integer.toString(stockItems.get(i).getQty()) + ",";
			eachLine += Double.toString(stockItems.get(i).getPrice()) + ",";
			eachLine += stockItems.get(i).getUseBy() + ",";
		}
		BufferedWriter stockWriter = new BufferedWriter(new FileWriter(stock));
		stockWriter.write(eachLine);
		stockWriter.close();
		

		//Writing order items to file;
		eachLine = "";
		File orderItem = new File("orderitems.txt");
		i = 0;
		
		for (; i < orderItems.size(); i++)	{
			eachLine += Integer.toString(orderItems.get(i).getOrderItmID()) + ",";
			eachLine += Integer.toString(orderItems.get(i).getProduct().getProductID()) + ",";
			eachLine += Integer.toString(orderItems.get(i).getqty()) + ",";
			eachLine += Double.toString(orderItems.get(i).getPrice()) + ",";
		}
		BufferedWriter ordItWriter = new BufferedWriter(new FileWriter(orderItem));
		ordItWriter.write(eachLine);
		ordItWriter.close();

		
		//Writing sales to file
		eachLine = "";
		File sale = new File("sales.txt");
		i = 0;
		
		for (; i < sales.size(); i++)	{
			eachLine += Integer.toString(sales.get(i).getTransID()) + ",";
			eachLine += Double.toString(sales.get(i).getAmount()) + ",";
			for (; j < sales.get(i).getItems().size(); j++)	{
				eachLine += Integer.toString(sales.get(i).getItems().get(j).getStockItmID()) + ",";
			}
			eachLine += Integer.toString(sales.get(i).getCustID()) + ",";
			eachLine += Integer.toString(sales.get(i).getCardNumb()) + ",";
		}
		BufferedWriter saleWriter = new BufferedWriter(new FileWriter(sale));
		saleWriter.write(eachLine);
		saleWriter.close();
		
		//Writing Returns to file
		eachLine = "";
		File returns = new File("returnsList.txt");
		i = 0;
		
		for (; i < returnsList.size(); i++)	{
			eachLine += Integer.toString(returnsList.get(i).getTransID()) + ",";
			eachLine += Double.toString(returnsList.get(i).getAmount()) + ",";
			for (; j < returnsList.get(i).getItems().size(); j++)	{
				eachLine += Integer.toString(returnsList.get(i).getItems().get(j).getStockItmID()) + ",";
			}
			eachLine += Integer.toString(returnsList.get(i).getCustID()) + ",";
			eachLine += Integer.toString(returnsList.get(i).getCardNumb()) + ",";
		}
		BufferedWriter returnsWriter = new BufferedWriter(new FileWriter(returns));
		returnsWriter.write(eachLine);
		returnsWriter.close();
		
		
		//Writing Account to file
		File account = new File("ac.txt");
		BufferedWriter accountWriter = new BufferedWriter(new FileWriter(account));
		accountWriter.write("" + ac.getAmount());
		accountWriter.close();
	}
}
