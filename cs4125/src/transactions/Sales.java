package transactions;

import java.util.ArrayList;
import goods.StockItem;

public class Sales extends Transactions	{
	
	public Sales(int transID, double amount, ArrayList<StockItem> items, int custID, String cardNumb)	{
		super(transID, amount, items, custID, cardNumb);
	}
}