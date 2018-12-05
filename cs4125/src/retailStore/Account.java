package retailStore;

//Implements Singleton Design Pattern
public class Account 
{
	private static Account ac = new Account(0);
	private double balance;
	
	private Account(double balance)
	{
		this.balance = balance;
	}

	public static Account instanceOf()
	{
		return ac;
	}
	
	public double getAmount()
	{
		return balance;
	}
	public double updateAmount(double amount, boolean sale)
	{
		if (sale)
		{
			this.balance += amount;
		}
		else
		{
			this.balance -= amount;
		}
		return balance;
	}	
	
	public boolean paymentSuccessful (double purchase) {
		
		if (balance - purchase > 0) {
			balance -= purchase;
			return true;
		}
		return false;		
	}
}
