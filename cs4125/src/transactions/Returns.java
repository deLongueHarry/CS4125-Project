package transactions;

import java.util.List;
import goods.StockItem;

public class Returns extends Transactions{
	
	public Returns(int transID, double amount, List<StockItem> items, int custID, String cardNumb)	{
		super(transID, amount, items, custID, cardNumb);
	}
}