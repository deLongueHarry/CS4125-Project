package dataPersistence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import employee.*;
import goods.*;
import transactions.*;
import customer.Customer;
import account.Account;

//Writes data to files
//Author: Alex
public class DataPersistence {
	public int i, j, listSize;
	public String eachLine = "";
	public DataPersistence() {}
	
	//Writing employees to file
	public void employeesToFile(ArrayList <Employee> employees)	throws IOException	{
		
		File emp = new File("employees.txt");
		emp.createNewFile();
		
		for (i = 0; i < employees.size(); i++)	{
			
			Employee temp = employees.get(i);
			
			if (temp instanceof StockEmployee) 
				eachLine += "s";
			else if (temp instanceof Manager) 
				eachLine += "m";
			
			eachLine += Integer.toString(temp.getID()) + "," + temp.getName() + ","
						+ temp.getPassword() + "," + temp.getAddress() + ","
							+ temp.getPhoneNo() + ",";
			
			if (i != employees.size() - 1)
				eachLine += "\n";
			
		}
		BufferedWriter empWriter = new BufferedWriter(new FileWriter(emp));
		empWriter.write(eachLine);
		empWriter.close();
	}
	
	//Writing customers to file
	public void customersToFile(ArrayList <Customer> customers) throws IOException	{
		
		File cust = new File("customers.txt");
		cust.createNewFile();
		eachLine = "";
		
		for (i = 0; i < customers.size(); i++) {
			
			Customer temp = customers.get(i);
			listSize = temp.getAllergens().size();
			
			eachLine += Integer.toString(temp.getCustID()) + "," + temp.getName() + ","
						+ temp.getCustPoints() + "," + temp.getCreditCard() + ",";
			
			for (j = 0; j < listSize - 1; j++)	{
				eachLine += temp.getAllergens().get(j) + "/";
			}
			eachLine += temp.getAllergens().get(listSize - 1) + ",";
			
			for (j = 0; j < temp.getVouchers().size(); j++)	{
				eachLine += Integer.toString(temp.getVouchers().get(j).getVoucherNo()) + ":"
							+ Double.toString(temp.getVouchers().get(j).getAmount()) + ",";	
			}
			
			if (i != customers.size() - 1)
				eachLine += "\n";
		}
		BufferedWriter custWriter = new BufferedWriter(new FileWriter(cust));
		custWriter.write(eachLine);
		custWriter.close();
	}
	
	//Writing products to file
	public void productsToFile(ArrayList <Product> products) throws IOException	{
		
		File prod = new File("products.txt");
		prod.createNewFile();
		eachLine = "";

		for (i = 0; i < products.size(); i++)	{
			
			Product temp = products.get(i);
			listSize = temp.getAllergens().size();
			
			eachLine += Integer.toString(temp.getProductID()) + ","
						+ temp.getProductName() + "," + temp.getType() + "," + temp.getCompany() + "," 
							+  Double.toString(temp.getCostPrice()) + Integer.toString(temp.getMinimumOrder()) + "," + ",";
			
			for (j = 0; j < listSize - 1; j++) {
				eachLine += temp.getAllergens().get(j) + "/";
			}
			eachLine += temp.getAllergens().get(listSize - 1) + ",";
			
			if (i != products.size() - 1)
				eachLine += "\n";
		}
		BufferedWriter prodWriter = new BufferedWriter(new FileWriter(prod));
		prodWriter.write(eachLine);
		prodWriter.close();
	}
	
	//Writing orders to file
	public void ordersToFile(ArrayList <Order> orders) 	throws IOException	{
		
		File ord = new File("orders.txt");
		ord.createNewFile();
		eachLine = "";
		
		for (i = 0; i < orders.size(); i++)	{
			
			Order temp = orders.get(i);
			listSize = temp.getOrder().size();
			
			eachLine += Integer.toString(temp.getOrderID()) + ",";
			
			for (j = 0; j < listSize; j++)	{
				eachLine += Integer.toString(temp.getOrder().get(j).getItmID()) + "/";
			}
			eachLine += temp.getOrder().get(listSize - 1) + ","	+ temp.getDateOrdered() + ","
						+ Integer.toString(temp.getEmp().getID()) + ",";
			
			if (temp.getPaid())
				eachLine += "true,";
			else
				eachLine += "false,";
			
			if (i != orders.size() - 1)
				eachLine += "\n";
		}
		BufferedWriter ordWriter = new BufferedWriter(new FileWriter(ord));
		ordWriter.write(eachLine);
		ordWriter.close();
	}
	
	//Writing stock items to file
	public void stockItemsToFile(ArrayList <StockItem> stockItems) throws IOException	{
		
		File stock = new File("stockitems.txt");
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
		BufferedWriter stockWriter = new BufferedWriter(new FileWriter(stock));
		stockWriter.write(eachLine);
		stockWriter.close();
	}
	
	//Writing order items to file;
	public void orderItemsToFile(ArrayList <OrderItem> orderItems) throws IOException	{
		
		File orderItem = new File("orderitems.txt");
		orderItem.createNewFile();
		eachLine = "";
		
		for (i = 0; i < orderItems.size(); i++)	{
			
			OrderItem temp = orderItems.get(i);
			
			eachLine += Integer.toString(temp.getItmID()) + ","
						+ Integer.toString(temp.getProduct().getProductID()) + ","
							+ Integer.toString(temp.getQty()) + ","	+ Double.toString(temp.getPrice()) + ",";
			
			if (i != orderItems.size() - 1)
				eachLine += "\n";
		}
		
		BufferedWriter ordItWriter = new BufferedWriter(new FileWriter(orderItem));
		ordItWriter.write(eachLine);
		ordItWriter.close();
	}
		
	//Writing sales to file
	public void salesToFile(ArrayList <Transactions> sales) throws IOException	{
		
		File sale = new File("sales.txt");
		sale.createNewFile();
		eachLine = "";
		
		for (i = 0; i < sales.size(); i++)	{
			
			Transactions temp = sales.get(i);
			listSize = temp.getItems().size();
			
			eachLine += Integer.toString(temp.getTransID()) + ","
						+ Double.toString(temp.getAmount()) + ",";
			
			for (j = 0; j < listSize; j++)	{
				eachLine += Integer.toString(temp.getItems().get(j).getItmID()) + "/";
			}
			eachLine += temp.getItems().get(listSize - 1) + "," + Integer.toString(temp.getCustID()) 
						+ "," + temp.getCardNumb() + ",";
			
			if (i != sales.size() - 1)
				eachLine += "\n";
		}
		BufferedWriter saleWriter = new BufferedWriter(new FileWriter(sale));
		saleWriter.write(eachLine);
		saleWriter.close();
	}
	
	//Writing Returns to file
	public void returnsToFile(ArrayList<Transactions> returnsList) throws IOException	{
		
		File returns = new File("returnsList.txt");
		returns.createNewFile();
		eachLine = "";
		
		for (i = 0; i < returnsList.size(); i++)	{
			
			Transactions temp = returnsList.get(i);
			listSize = temp.getItems().size();
			
			eachLine += Integer.toString(temp.getTransID()) + "," + Double.toString(temp.getAmount()) + ",";
			
			for (j = 0; j < listSize; j++)	{
				eachLine += Integer.toString(temp.getItems().get(j).getItmID()) + "/";
			}
			eachLine += temp.getItems().get(listSize - 1) + "," + "," + Integer.toString(temp.getCustID()) + "," + temp.getCardNumb() + ",";
			
			if (i != returnsList.size() - 1)
				eachLine += "\n";
		}
		BufferedWriter returnsWriter = new BufferedWriter(new FileWriter(returns));
		returnsWriter.write(eachLine);
		returnsWriter.close();
	}
		
	//Writing Account to file
	public void accountToFile(Account ac) throws IOException	{
		
		File account = new File("ac.txt");
		account.createNewFile();
		
		BufferedWriter accountWriter = new BufferedWriter(new FileWriter(account));
		accountWriter.write("" + ac.getAmount());
		accountWriter.close();
	}
}
