package transactions;

import java.util.List;
import goods.StockItem;
//Implements factory method for transactions
//Author: Alex
public class TransactionsFactory {
	
	public Transactions getTransactions(String transactionsType, int transID, double amount, List<StockItem> items, int custID, String cardNumb)	{
		transactionsType = transactionsType.toLowerCase();
		if (transactionsType == null)	{
			return null;
		}
		else if (transactionsType == "sales")	{
			return new Sales(transID, amount, items, custID, cardNumb);
		}
		else if (transactionsType == "returns")	{
			return new Returns(transID, amount, items, custID, cardNumb);
		}
		else
			return null;
	}
}
