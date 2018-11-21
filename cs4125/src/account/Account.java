package account;

public class Account 
{
	private int amount;
	
	public Account(int xAm)
	{
		xAm = amount;
	}
	public int getAmount()
	{
		return amount;
	}
	public int updateAmount(double aAmount, boolean sale)
	{
		if(sale = true)
			aAmount += amount;
		else
			aAmount -= amount;
		return amount;
	}
}
