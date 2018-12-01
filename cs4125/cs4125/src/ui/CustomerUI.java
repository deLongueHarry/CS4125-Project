package ui;

public class CustomerUI implements UI {
	
	public void startInterface() {

		TransactionsUI transactions = new TransactionsUI();
		transactions.startInterface();
	}
}
