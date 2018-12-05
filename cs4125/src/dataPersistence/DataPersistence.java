package dataPersistence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import employee.*;
import retailStore.Account;
import retailStore.StoreFacade;
import stock.*;
import transactions.*;
import customer.Customer;

//Writes data to files
//Author: Alex
public class DataPersistence {
	private int i, j;
	private int listSize;
	private String eachLine;
	
	public DataPersistence() {
		eachLine = "";
	}
	
	//Runs the DataPersistenceLayer
	public void run() throws IOException	{
		employeesToFile(StoreFacade.employees);
		customersToFile(StoreFacade.customers);
		productsToFile(StoreFacade.products);
		ordersToFile(StoreFacade.orders);
		stockItemsToFile(StoreFacade.stockItems);
		orderItemsToFile(StoreFacade.orderItems);
		salesToFile(StoreFacade.sales);
		returnsToFile(StoreFacade.returnsList);
		accountToFile(StoreFacade.ac);
	}
	
	//Writing employees to file
	public void employeesToFile(List <Employee> employees)	throws IOException	{
		
		File emp = new File("data/employees.txt");
		emp.createNewFile();	// Only creates new file if it doesn't already exist
		
		Criteria crit = new CriteriaManager();
		List<Employee> man = crit.meetCriteria(employees);
		for (i = 0; i < employees.size(); i++)	{
			
			Employee temp = employees.get(i);
			
			for (j = 0; j < man.size(); j++)	{
				if (employees.get(i) == man.get(j))	{
					eachLine += "m";
				}
				else	{
					eachLine += "s";
				}
			}
			
			eachLine += Integer.toString(temp.getID()) + "," + temp.getName() + ","
						+ temp.getPassword() + "," + temp.getAddress() + ","
							+ temp.getPhoneNo() + ",";
			
			if (i != employees.size() - 1)
				eachLine += "\n";
			
		}
		try (BufferedWriter empWriter = new BufferedWriter(new FileWriter(emp));)	{
			empWriter.write(eachLine);
		}
	}
	
	//Writing customers to file
	public void customersToFile(List <Customer> customers) throws IOException	{
		
		File cust = new File("data/customers.txt");
		cust.createNewFile();
		eachLine = "";
		
		for (i = 0; i < customers.size(); i++) {

			Customer temp = customers.get(i);
			List<String> allergenList = new ArrayList<>();
			for (j = 0; j < temp.getAllergens().size(); j++)	{
				allergenList.add(temp.getAllergens().get(j));
			}
			
			eachLine += Integer.toString(temp.getCustID()) + "," + temp.getCustPoints() 
						+ "," + temp.getName() + "," + temp.getCreditCard() + ",";
			
			if (allergenList != null) {
				listSize = allergenList.size();
				
				
				for (j = 0; j < listSize; j++)	{
					eachLine += allergenList.get(j);
					if (j != listSize - 1)
						eachLine += "/";
				}
			}
			eachLine += ",";
			
			for (j = 0; j < temp.getVouchers().size(); j++)	{
				eachLine += Integer.toString(temp.getVouchers().get(j).getVoucherNo()) + ":"
							+ Double.toString(temp.getVouchers().get(j).getAmount());
				if (j != temp.getVouchers().size() - 1)
					eachLine += "/";
				else
					eachLine += ",";
			}
			
			if (i != customers.size() - 1)
				eachLine += "\n";
		}
		
		try (BufferedWriter custWriter = new BufferedWriter(new FileWriter(cust));)	{
			custWriter.write(eachLine);
		}
	}
	
	//Writing products to file
	public void productsToFile(List <Product> products) throws IOException	{
		
		File prod = new File("data/products.txt");
		prod.createNewFile();
		eachLine = "";

		for (i = 0; i < products.size(); i++)	{
			
			Product temp = products.get(i);
			List<String> allergenList = new ArrayList<>();
			for (j = 0; j < temp.getAllergens().size(); j++)	{
				allergenList.add(temp.getAllergens().get(j));
			}
			
			eachLine += Integer.toString(temp.getProductID()) + ","	+ temp.getProductName() + "," + temp.getType() + "," 
							+ temp.getCompany() + "," +  Double.toString(temp.getCostPrice()) + "," 
								+ Integer.toString(temp.getMinimumOrder()) + ",";
			
			listSize = allergenList.size();
			for (j = 0; j < listSize; j++)	{
				eachLine += allergenList.get(j);
				if (j != listSize - 1)
					eachLine += "/";
			}
			eachLine += ",";			
			if (i != products.size() - 1)
				eachLine += "\n";
		}
		
		try (BufferedWriter prodWriter = new BufferedWriter(new FileWriter(prod));)	{
			prodWriter.write(eachLine);
		}
	}
	
	//Writing orders to file
	public void ordersToFile(List <Order> orders) 	throws IOException	{
		
		File ord = new File("data/orders.txt");
		ord.createNewFile();
		eachLine = "";
		
		for (i = 0; i < orders.size(); i++)	{
			
			Order temp = orders.get(i);
			eachLine += Integer.toString(temp.getOrderID()) + ",";
			
			listSize = temp.getOrderItems().size();
			for (j = 0; j < listSize; j++)	{
				eachLine += Integer.toString(temp.getOrderItems().get(j).getItmID());
				if (j != listSize - 1)
					eachLine += "/";
			}	
			
			eachLine += "," + temp.getDateOrdered() + ",";
			if (temp.getEmp() == null)
				eachLine += "0,";
			else
				eachLine += temp.getEmp().getID() + ",";
			
			eachLine += (temp.isApproved() ? "true," : "false,")
						+ (temp.isPaid() ? "true," : "false,");
			
			if (i != orders.size() - 1)
				eachLine += "\n";
		}
		try (BufferedWriter ordWriter = new BufferedWriter(new FileWriter(ord));)	{
			ordWriter.write(eachLine);
		}
	}
	
	//Writing stock items to file
	public void stockItemsToFile(List <StockItem> stockItems) throws IOException	{
		
		File stock = new File("data/stockitems.txt");
		stock.createNewFile();
		eachLine = "";
		
		for (i = 0; i < stockItems.size(); i++)	{
			
			StockItem temp = stockItems.get(i);
			eachLine += Integer.toString(temp.getItmID()) + ","
						+ Integer.toString(temp.getProduct().getProductID()) + ","
							+ Integer.toString(temp.getQty()) + ","	+ Double.toString(temp.getPrice()) + ","
								+ temp.getUseBy() + ",";
			
			if (i != stockItems.size() - 1)
				eachLine += "\n";
		}
		try (BufferedWriter stockWriter = new BufferedWriter(new FileWriter(stock));)	{
			stockWriter.write(eachLine);
		}
	}
	
	//Writing order items to file
	public void orderItemsToFile(List <OrderItem> orderItems) throws IOException	{
		
		File orderItem = new File("data/orderitems.txt");
		orderItem.createNewFile();
		eachLine = "";
		
		for (i = 0; i < orderItems.size(); i++)	{
			
			OrderItem temp = orderItems.get(i);
			eachLine += Integer.toString(temp.getItmID()) + ","
						+ Integer.toString(temp.getProduct().getProductID()) + ","
							+ Integer.toString(temp.getQty()) + ",";
			
			if (i != orderItems.size() - 1)
				eachLine += "\n";
		}
		
		try (BufferedWriter ordItWriter = new BufferedWriter(new FileWriter(orderItem));)	{
			ordItWriter.write(eachLine);
		}
	}
		
	//Writing sales to file
	public void salesToFile(List <Transactions> sales) throws IOException	{
		
		File sale = new File("data/sales.txt");
		sale.createNewFile();
		eachLine = "";
		
		for (i = 0; i < sales.size(); i++)	{
			
			Transactions temp = sales.get(i);
			listSize = temp.getItems().size();
			
			eachLine += Integer.toString(temp.getTransID()) + ","
						+ Double.toString(temp.getAmount()) + ",";
			
			for (j = 0; j < listSize; j++)	{
				eachLine += Integer.toString(temp.getItems().get(j).getItmID());
				if (j != listSize - 1)
					eachLine += "/";
			}
			eachLine += "," + Integer.toString(temp.getCustID()) + "," + temp.getCardNumb() + ",";
			
			if (i != sales.size() - 1)
				eachLine += "\n";
		}
		try (BufferedWriter saleWriter = new BufferedWriter(new FileWriter(sale));)	{
			saleWriter.write(eachLine);
		}
	}
	
	//Writing Returns to file
	public void returnsToFile(List<Transactions> returnsList) throws IOException	{
		
		File returns = new File("data/returnsList.txt");
		returns.createNewFile();
		eachLine = "";
		
		for (i = 0; i < returnsList.size(); i++)	{
			
			Transactions temp = returnsList.get(i);
			listSize = temp.getItems().size();
			
			eachLine += Integer.toString(temp.getTransID()) + "," + Double.toString(temp.getAmount()) + ",";
			
			for (j = 0; j < listSize; j++)	{
				eachLine += Integer.toString(temp.getItems().get(j).getItmID());
				if (j != listSize - 2)
					eachLine += "/";
			}
			eachLine += "," + Integer.toString(temp.getCustID()) + "," + temp.getCardNumb() + ",";
			
			if (i != returnsList.size() - 1)
				eachLine += "\n";
		}
		try (BufferedWriter returnsWriter = new BufferedWriter(new FileWriter(returns));)	{
			returnsWriter.write(eachLine);
		}
	}
		
	//Writing Account to file
	public void accountToFile(Account ac) throws IOException	{
		
		File account = new File("data/ac.txt");
		account.createNewFile();
		
		try (BufferedWriter accountWriter = new BufferedWriter(new FileWriter(account));)	{
			accountWriter.write("" + ac.getAmount());
		}
	}
}