package dataPersistence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
	public void employeesToFile(List <Employee> employees)	throws IOException	{
		
		File emp = new File("employees.txt");
		emp.createNewFile();
		Criteria crit = new CriteriaManager();
		List<Employee> man = crit.meetCriteria(employees);
		for (i = 0; i < employees.size(); i++)	{
			
			Employee temp = employees.get(i);
			
			for (int j = 0; j < man.size(); j++)	{
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
		BufferedWriter empWriter = new BufferedWriter(new FileWriter(emp));
		empWriter.write(eachLine);
		empWriter.close();
	}
	
	//Writing customers to file
	public void customersToFile(List <Customer> customers) throws IOException	{
		
		File cust = new File("customers.txt");
		cust.createNewFile();
		eachLine = "";
		
		for (i = 0; i < customers.size(); i++) {

			Customer temp = customers.get(i);	
			//ArrayList allergenList = (ArrayList) temp.getAllergens().clone();
			List<String> allergenList = new ArrayList<>();
			for (int j = 0; j < temp.getAllergens().size(); j++)	{
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
		BufferedWriter custWriter = new BufferedWriter(new FileWriter(cust));
		custWriter.write(eachLine);
		custWriter.close();
	}
	
	//Writing products to file
	public void productsToFile(List <Product> products) throws IOException	{
		
		File prod = new File("products.txt");
		prod.createNewFile();
		eachLine = "";

		for (i = 0; i < products.size(); i++)	{
			
			Product temp = products.get(i);
			//ArrayList allergenList = (ArrayList) temp.getAllergens().clone();
			List<String> allergenList = new ArrayList<>();
			for (int j = 0; j < temp.getAllergens().size(); j++)	{
				allergenList.add(temp.getAllergens().get(j));
			}
			
			eachLine += Integer.toString(temp.getProductID()) + ","	+ temp.getProductName() + "," + temp.getType() + "," 
							+ temp.getCompany() + "," +  Double.toString(temp.getCostPrice()) + "," 
								+ Integer.toString(temp.getMinimumOrder()) + ",";
			
			if (allergenList != null) {
				listSize = allergenList.size();
				
				
				for (j = 0; j < listSize; j++)	{
					eachLine += allergenList.get(j);
					if (j != listSize - 1)
						eachLine += "/";
				}
			}

			eachLine += ",";
			
			if (i != products.size() - 1)
				eachLine += "\n";
		}
		BufferedWriter prodWriter = new BufferedWriter(new FileWriter(prod));
		prodWriter.write(eachLine);
		prodWriter.close();
	}
	
	//Writing orders to file
	public void ordersToFile(List <Order> orders) 	throws IOException	{
		
		File ord = new File("orders.txt");
		ord.createNewFile();
		eachLine = "";
		
		for (i = 0; i < orders.size() - 1; i++)	{
			
			Order temp = orders.get(i);
			eachLine += Integer.toString(temp.getOrderID()) + ",";
			
			if (temp.getOrderItems() != null)	{
				listSize = temp.getOrderItems().size();
				
				for (j = 0; j < listSize; j++)	{
					eachLine += Integer.toString(temp.getOrderItems().get(j).getItmID());
					if (j != listSize - 1)
						eachLine += "/";
				}	
				
				eachLine += "," + temp.getDateOrdered() + "," + temp.getEmp().getID() + ","
						+ (temp.isApproved() ? "true," : "false,")
							+ (temp.isPaid() ? "true," : "false,")
								+ temp.getDateOrdered() + ",";
			}
			
			

			if (i != orders.size() - 2)
				eachLine += "\n";
		}
		BufferedWriter ordWriter = new BufferedWriter(new FileWriter(ord));
		ordWriter.write(eachLine);
		ordWriter.close();
	}
	
	//Writing stock items to file
	public void stockItemsToFile(List <StockItem> stockItems) throws IOException	{
		
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
	public void orderItemsToFile(List <OrderItem> orderItems) throws IOException	{
		
		File orderItem = new File("orderitems.txt");
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
		
		BufferedWriter ordItWriter = new BufferedWriter(new FileWriter(orderItem));
		ordItWriter.write(eachLine);
		ordItWriter.close();
	}
		
	//Writing sales to file
	public void salesToFile(List <Transactions> sales) throws IOException	{
		
		File sale = new File("sales.txt");
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
		BufferedWriter saleWriter = new BufferedWriter(new FileWriter(sale));
		saleWriter.write(eachLine);
		saleWriter.close();
	}
	
	//Writing Returns to file
	public void returnsToFile(List<Transactions> returnsList) throws IOException	{
		
		File returns = new File("returnsList.txt");
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