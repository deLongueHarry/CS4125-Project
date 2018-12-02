package transactions;

import java.util.List;

import stock.StockItem;

public class Returns extends Transactions{
	
	public Returns(int transID, double amount, List<StockItem> items, int custID, String cardNumb)	{
		super(transID, amount, items, custID, cardNumb);
	}
}