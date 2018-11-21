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
	public int i = 0;
	public String eachLine = "";
	public int j = 0;
	public DataPersistence() {}
	
	//Writing employees to file
	public void employeesToFile(ArrayList <Employee> employees)	throws IOException	{
		File emp = new File("employees.txt");
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
	}
	
	//Writing customers to file
	public void customersToFile(ArrayList <Customer> customers) throws IOException	{
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
	}
	
	//Writing products to file
	//Writing products to file
	public void productsToFile(ArrayList <Product> products) throws IOException	{
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
	}
	
	//Writing orders to file
	//Writing orders to file
	public void ordersToFile(ArrayList <Order> orders) 	throws IOException	{
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
	}
	
	//Writing stock items to file
	//Writing stock items to file
	public void stockItemsToFile(ArrayList <StockItem> stockItems) throws IOException	{
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
	}
	
	//Writing order items to file
	//Writing order items to file;
	public void orderItemsToFile(ArrayList <OrderItem> orderItems) throws IOException	{
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
	}
		
	//Writing sales to file
	public void salesToFile(ArrayList <Transactions> sales) throws IOException	{
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
	}
	
	//Writing Returns to file
	public void returnsToFile(ArrayList<Transactions> returnsList) throws IOException	{
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
	}
		
	//Writing Account to file
	public void accountToFile(Account ac) throws IOException	{
		File account = new File("ac.txt");
		BufferedWriter accountWriter = new BufferedWriter(new FileWriter(account));
		accountWriter.write("" + ac.getAmount());
		accountWriter.close();
	}
}