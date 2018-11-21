package customer;

import java.util.ArrayList;
import transactions.Voucher;

public class Customer {
	private int custID, custPoints;
	private String custName, creditCard;
	private ArrayList<String> allergens;
	private ArrayList<Voucher> vouchers;
	
	public Customer(int custID, String custName, int custPoints, ArrayList<String> allergens, String creditCard, ArrayList<Voucher> vouchers)	{
		this.custID = custID;
		this.custName = custName;
		this.custPoints = custPoints;
		this.allergens = allergens;
		this.creditCard = creditCard;
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
		return custPoints;
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
	
	public String getCreditCard()
	{
		return creditCard;
	}
	
	public void setCreditCard(String creditCard)
	{
		this.creditCard = creditCard;
	}

	public String getName() {
		return custName;
	}

	public void setName(String custName) {
		this.custName = custName;
	}
}
