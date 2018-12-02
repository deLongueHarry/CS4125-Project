package retailStore;

//Implements Singleton Design Pattern
public class Account 
{
	private static Account ac = new Account(0);
	private double amount;
	
	private Account(double xAm)
	{
		this.amount = xAm;
	}

	public static Account instanceOf()
	{
		return ac;
	}
	
	public double getAmount()
	{
		return amount;
	}
	public double updateAmount(double aAmount, boolean sale)
	{
		if (sale)
		{
			this.amount += aAmount;
		}
		else
		{
			this.amount -= aAmount;
		}
		return amount;
	}	
}
