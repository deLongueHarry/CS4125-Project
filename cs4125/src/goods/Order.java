package goods;

import java.util.ArrayList;
import employee.Employee;

public class Order
{
	private int orderID;
	private ArrayList<OrderItem> orderItems;
	private String dateOrdered;
	private Employee emp;
	private boolean approved;
	private boolean paid;
	
	public Order()	{
		
	}
	
	public Order(int orderID, ArrayList<OrderItem> orderItems, String dateOrdered, Employee emp, boolean approved, boolean paid) 
	{
		this.orderID = orderID;
		this.orderItems = orderItems;
		this.dateOrdered = dateOrdered;
		this.emp = emp;
		this.approved = approved;
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
	
	public boolean isPaid()
	{
		return paid;
	}
	
	public void setPaid(boolean paid)
	{
		this.paid = paid;
	}
	
	public boolean isApproved()
	{
		return approved;
	}
	
	public void Approve()
	{
		this.approved = true;
	}	
	
	public ArrayList<OrderItem> getOrderItems()
	{
		return orderItems;
	}
	
	public void setOrderItems(ArrayList<OrderItem> order)
	{
		this.orderItems = orderItems;
	}
}