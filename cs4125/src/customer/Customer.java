package customer;

import java.util.ArrayList;
import transactions.Voucher;

public class Customer {
	private int custID;
	private int custPoints;
	private ArrayList<Integer> creditCards;
	private ArrayList<String> allergens;
	private ArrayList<Voucher> vouchers;
	
	public Customer(int custID)	{
		this.custID = custID;
		this.custPoints = 0;
	}
	
	public Customer(int custID, int custPoints, ArrayList<Integer> creditCards, ArrayList<String> allergens, ArrayList<Voucher> vouchers)	{
		this.custID = custID;
		this.custPoints = custPoints;
		this.creditCards = creditCards;
		this.allergens = allergens;
		this.vouchers = vouchers;
	}
	
	public void addPoints(int points)	{
		this.custPoints += points;
	}
	
	public int getCustID()	{
		return(custID);
	}
	
	public void setCustID(int custID)	{
		this.custID = custID;
	}
	
	public int getCustPoints()	{
		return(custPoints);
	}
	
	public ArrayList<String> getAllergens()	{
		return allergens;
	}
	
	public void setAllergens(ArrayList<String> allergens)	{
		this.allergens = allergens;
	}
	
	public ArrayList<Voucher> getVouchers()	{
		return vouchers;
	}
	
	public void setVouchers(ArrayList<Voucher> vouchers)	{
		this.vouchers = vouchers;
	}
	
	public ArrayList<Integer> getCreditCards()
	{
		return creditCards;
	}
	
	public void setCreditCards(ArrayList<Integer> creditCards)
	{
		this.creditCards = creditCards;
	}
}
