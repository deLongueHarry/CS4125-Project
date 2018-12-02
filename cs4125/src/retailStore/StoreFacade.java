package retailStore;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import customer.Customer;
import dataPersistence.DataPersistence;
import employee.Employee;
import stock.Order;
import stock.OrderItem;
import stock.Product;
import stock.StockItem;
import transactions.Transactions;
import transactions.TransactionsFactory;
import transactions.Voucher;
import ui.StoreUI;
import ui.UI;

//Implements Facade Design Pattern
public class StoreFacade {
	
	private Product temp;
	public static List<Employee> employees = new ArrayList<>();
	public static List<Customer> customers = new ArrayList<>();
	public static List<Product> products = new ArrayList<>();
	public static List<Order> orders = new ArrayList<>();
	public static List<StockItem> stockItems = new ArrayList<>();
	public static List<OrderItem> orderItems = new ArrayList<>();
	public static List<Transactions> sales = new ArrayList<>();
	public static List<Transactions> returnsList = new ArrayList<>();
	public static Account ac = Account.instanceOf();
	
	public StoreFacade() {
		temp = null;
	}
	
	//Runs facade
	public void run() throws IOException, ParseException {
		
		readEmployees();
		readCustomers();
		readProducts();
		readStockItems();
		readOrderItems();
		readOrders();
		readSales();
		readReturns();
		readAccount();
		
		checkStockLevels();
		
		//Launches UI
		UI store = new StoreUI();
		store.startInterface();
				
		//Reads lists into files
		DataPersistence d = new DataPersistence();
		d.run();
	}
	
	// Loads employees into the system from csv file
	// Author: Michael
	public void readEmployees() throws IOException	{
			
		File empFile = new File("data/employees.txt");
		Scanner empScanner = new Scanner(empFile);

		while(empScanner.hasNext()) {
			
			String[] empStr = empScanner.nextLine().split(",");
			Employee tempEmp = new Employee();
			
			String empType = empStr[0].substring(0, 1);
			empStr[0] = empStr[0].substring(1);
			
			if (empType.equalsIgnoreCase("m"))
				tempEmp.setType("manager");
			else if (empType.equalsIgnoreCase("s"))
				tempEmp.setType("stock employee");
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
	}
	
	// Loads customers into the system from csv file
	// Author: Michael
	public void readCustomers() throws IOException	{
		
		File custFile = new File("data/customers.txt");
		Scanner custScanner = new Scanner(custFile);		
	
		while(custScanner.hasNext()) {
			
			ArrayList<String> allergenList = new ArrayList<>();
			ArrayList<Voucher> vouchers = new ArrayList<>();
			String[] custStr = custScanner.nextLine().split(",");
						
			if (custStr[4].equals("")) {
				allergenList = null;
			}
			else {
				String[] allergens = custStr[4].split("/");
				for (int i = 0; i < allergens.length; i++) {
					allergenList.add(allergens[i]);
				}			
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
	}
	
	// Loads products into the system from csv file
	// Author: Michael
	public void readProducts()	throws IOException{
		
		File prodFile = new File("data/products.txt");
		Scanner prodScanner = new Scanner(prodFile);
		
		while(prodScanner.hasNext()) {
			
			ArrayList<String> allergenList = new ArrayList<>();
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
	}
	
	// Loads orders into the system from csv file
	// Author: Michael
	public void readOrders() throws IOException{
		
		File orderFile = new File("data/orders.txt");
		Scanner orderScanner = new Scanner(orderFile);
		
		while(orderScanner.hasNext()) {
			String[] orderStr = orderScanner.nextLine().split(",");
			boolean approved;
			boolean paid;
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
	
	// Loads stockItems into the system from csv file
	// Author: Michael
	public void readStockItems() throws IOException{
		
		File stockItmFile = new File("data/stockItems.txt");
		Scanner stockItmScanner = new Scanner(stockItmFile);		
		while(stockItmScanner.hasNext()) {
			
			String[] stockItmStr = stockItmScanner.nextLine().split(",");
			int prodID = Integer.parseInt(stockItmStr[1]);

			for (int i = 0; i < products.size(); i++) {
				if(prodID == products.get(i).getProductID()) {
					temp = products.get(i);
				}
			}
			StockItem tempStockItm = new StockItem(Integer.parseInt(stockItmStr[0]), temp, 
													Integer.parseInt(stockItmStr[2]), stockItmStr[4]);
			stockItems.add(tempStockItm);
		}
		stockItmScanner.close();
	}
	
	// Loads orderItems into the system from csv file
	// Author: Michael
	
	public void readOrderItems() throws IOException{
		
		File orderItmFile = new File("data/orderItems.txt");
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
	
	//Reads sales from text file
	public void readSales() throws IOException	{
		//Load sales into system
		TransactionsFactory fact = new TransactionsFactory();
		File salesFile = new File("data/sales.txt");
		Scanner salesScanner = new Scanner(salesFile);
		
		while(salesScanner.hasNext())	{
			String[] salesStr = salesScanner.nextLine().split(",");			
			String[] itemsList = salesStr[2].split("/");
			List<StockItem> items = new ArrayList<>();
			for (int i = 0; i < itemsList.length; i++)	{
				for (int j = 0; j < stockItems.size(); j++) {
					if (Integer.parseInt(itemsList[i]) == stockItems.get(j).getItmID())	{
						items.add(stockItems.get(j));
					}
				}
			}
			sales.add(fact.getTransactions("sales",
					Integer.parseInt(salesStr[0]), 
					Double.parseDouble(salesStr[1]),
					items, 
					Integer.parseInt(salesStr[3]), 
					salesStr[4]));
		}
		salesScanner.close();
	}
	
	//Reads returns from text file
	public void readReturns() throws IOException	{
		//Read returns into system
		TransactionsFactory fact = new TransactionsFactory();
		File returnsFile = new File("data/returns.txt");
		Scanner returnsScanner = new Scanner(returnsFile);
		
		while(returnsScanner.hasNext())	{
			String[] returnsStr = returnsScanner.nextLine().split(",");
			String[] itemsList = returnsStr[2].split("/");
			List<StockItem> items = new ArrayList<>(); //SalesStr[2]
			for (int i = 0; i < itemsList.length; i++)	{
				for (int j = 0; j < stockItems.size(); j++) {
					if (Integer.parseInt(itemsList[i]) == stockItems.get(j).getItmID())	{
						items.add(stockItems.get(j));
					}
				}
			}			
			returnsList.add(fact.getTransactions("returns", Integer.parseInt(returnsStr[0]), Double.parseDouble(returnsStr[1]), items, Integer.parseInt(returnsStr[3]), returnsStr[4]));
		}
		returnsScanner.close();
	}
	
	//Reads account from text file
	public void readAccount() throws IOException	{
		File acFile = new File("data/ac.txt");
		Scanner acScanner = new Scanner(acFile);
		
		while(acScanner.hasNext())	{
			ac.updateAmount(Double.parseDouble(acScanner.nextLine()), true);
		}		
		acScanner.close();
	}

	// If stock is below a certain threshold or due to expire system generates orders automatically
	// Author: Michael
	public void checkStockLevels() throws ParseException {
		
        SimpleDateFormat useByFormat = new SimpleDateFormat("dd-MM-yy");
        ArrayList<OrderItem> items = new ArrayList<OrderItem>();
        
        Calendar c = Calendar.getInstance();   
        String todaysDate = useByFormat.format(c.getTime());
        
        c.set(Calendar.HOUR_OF_DAY, 18);
        c.set(Calendar.MINUTE, 50);
        Date COBtoday = c.getTime();
        
        c.add(Calendar.DATE, 7);
        Date COBnextWeek = c.getTime();
        
        c.add(Calendar.DATE, -8);
        Date expired = c.getTime();
        
        //System.out.println(shopHoursFormat.format(COBnextWeek));
	
		for (int i = 0; i < stockItems.size(); i++) {
			
			int threshold = 40;
			int typicalOrderQty = 80;
			
			StockItem stock = stockItems.get(i);
			Product prod = stock.getProduct();		
			Date productDate = useByFormat.parse(stock.getUseBy());
									
			if (expired.compareTo(productDate) > 0) {
				stockItems.remove(i);
			}
			
			// applies discount to products due to expire at end of day
			if (productDate.compareTo(COBtoday) < 0 ) {
				stock.setPrice(stock.getPrice() * 0.5);
			}
			
			if (stock.getQty() < threshold || productDate.compareTo(COBnextWeek) < 0) {
				
				int ID = 0;
				for (int j = 0; j < stockItems.size(); j++) {
									
					Product productToCompare = stockItems.get(j).getProduct();
					if (productToCompare.getProductName().equals(prod.getProductName())) 			
						items.add(new OrderItem((ID++), prod, typicalOrderQty/prod.getMinimumOrder()));
				}
			}					
		}
		
		if (items != null) {
			
			int orderID = 1;
			if (orders != null) {
				orderID = orders.get(orders.size() - 1).getOrderID();
			}
			
			// System generated orders are approved automatically
			Order todaysOrder = new Order(orderID, items, todaysDate, null, true, false);
			orders.add(todaysOrder);			
		}		
	}
}