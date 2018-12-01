package goods;

import java.util.ArrayList;
import employee.Employee;

public class Order
{
	private int orderID;
	private ArrayList<OrderItem> orderItems;
	private String dateOrdered;
	private Employee emp;
	private boolean paid;
	
	public Order()	{
		
	}
	
	public Order(int orderID, ArrayList<OrderItem> orderItems, String dateOrdered, Employee emp, boolean paid) 
	{
		this.orderID = orderID;
		this.orderItems = orderItems;
		this.dateOrdered = dateOrdered;
		this.emp = emp;
		this.paid = paid;
	}
	
	public int getOrderID()
	{
		return orderID;
	}
	
	public void setOrderID(int orderID)
	{
		this.orderID = orderID;
	}
	
	public String getDateOrdered()
	{
		return dateOrdered;
	}
	
	public void setDateOrdered(String dateOrdered)
	{
		this.dateOrdered = dateOrdered;
	}
	
	public Employee getEmp()
	{
		return emp;
	}
	
	public void setEmp(Employee emp)
	{
		this.emp = emp;
	}
	
	public boolean getPaid()
	{
		return paid;
	}
	
	public void setPaid(boolean paid)
	{
		this.paid = paid;
	}
	
	public ArrayList<OrderItem> getOrder()
	{
		return orderItems;
	}
	
	public void setOrder(ArrayList<OrderItem> order)
	{
		this.orderItems = orderItems;
	}
}