package other;

import java.util.ArrayList;
import goods.*;
import transactions.*;
import account.Account;
import ui.TransactionsUI;
import customer.Customer;

public class TransactionsTester {
	
	public static ArrayList<Product> products = new ArrayList<Product>();
	public static ArrayList<StockItem> stockItems = new ArrayList<StockItem>();
	public static ArrayList<Sales> sale = new ArrayList<Sales>();
	public static ArrayList<Returns> returnsList = new ArrayList<Returns>();
	public static ArrayList<Customer> customers = new ArrayList<Customer>();
	public static Account ac = new Account(1000);	
	
	public static void main(String[] args) {
		//create + populate ArrayLists
		fillArrayLists();
		
		TransactionsUI x = new TransactionsUI();
		x.startInterface();
	}
	
	public static void fillArrayLists()	{
		ArrayList<String> cokeAllergens = new ArrayList<>();
		ArrayList<String> yorkieAllergens = new ArrayList<>();
		yorkieAllergens.add("Milk");
		yorkieAllergens.add("Nuts");
		
		ArrayList<Voucher> vouchers = new ArrayList<>();
		ArrayList<Integer> creditCards = new ArrayList<>();
		
		Customer cust1 = new Customer(1, 0, creditCards, yorkieAllergens, vouchers);
		customers.add(cust1);
		
		Product coke = new Product(1, 1, 1.0, "coke zero", "Soft Drink", "Coca Cola", cokeAllergens);
		Product yorkie = new Product(2, 2, 1.0, "yorkie", "Chocolate Bar", "Nestle", yorkieAllergens);
		
		products.add(coke);
		products.add(yorkie);
		//public StockItem(int stockItmID, Product prod, int qty, double price, String useBy)
		stockItems.add(new StockItem(2, coke, 10, 5, "12/12/2020"));
		stockItems.add(new StockItem(1, yorkie, 30, 2, "1/1/2019"));
		
		ArrayList<StockItem> add = new ArrayList<StockItem>();
		add.add(new StockItem(1, yorkie, 10, 5, "25/12/2018"));
		
		sale.add(new Sales(10, 50, add, 30, 123));
		returnsList.add(new Returns(10, 50, add, 30, 123));
	}
	
}