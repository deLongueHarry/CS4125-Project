package transactions;

import java.util.List;
import goods.StockItem;

public class Transactions {
	private int transID;
	private double amount;
	private List<StockItem> items;
	private int custID;
	private String cardNumb;
	
	public Transactions()	{}
	
	public Transactions(int transID)	{
		this.transID = transID;
	}

	public Transactions(int transID, double amount, List<StockItem> items, int custID, String cardNumb)	{
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
	
	public List<StockItem> getItems()	{
		return items;
	}
	
	public void setItems(List<StockItem> items)	{
		this.items = items;
	}
	
	public int getCustID()	{
		return custID;
	}
	
	public void setCustID(int custID)	{
		this.custID = custID;
	}
	
	public String getCardNumb()	{
		return cardNumb;
	}
	
	public void setCardNumb(String cardNumb)	{
		this.cardNumb = cardNumb;
	}
}