package transactions;

import java.util.ArrayList;
import goods.StockItem;

public class Transactions {
	private int transID;
	private double amount;
	private ArrayList<StockItem> items;
	private int custID;
	private int cardNumb;
	
	public Transactions(int transID)	{
		this.transID = transID;
	}
	
	public Transactions(int transID, double amount, ArrayList<StockItem> items, int custID, int cardNumb)	{
		this.transID = transID;
		this.amount = amount;
		this.items = items;
		this.custID = custID;
		this.cardNumb = cardNumb;
	}
	
	public int getTransID()	{
		return transID;
	}
	
	public void setTransID(int transID)	{
		this.transID = transID;
	}
	
	public double getAmount()	{
		return amount;
	}
	
	public void setAmount(double amount)	{
		this.amount = amount;
	}
	
	public ArrayList<StockItem> getItems()	{
		return items;
	}
	
	public void setItems(ArrayList<StockItem> items)	{
		this.items = items;
	}
	
	public int getCustID()	{
		return custID;
	}
	
	public void setCustID(int custID)	{
		this.custID = custID;
	}
	
	public int getCardNumb()	{
		return cardNumb;
	}
	
	public void setCardNumb(int cardNumb)	{
		this.cardNumb = cardNumb;
	}
}