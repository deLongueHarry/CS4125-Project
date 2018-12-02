package customer;

import java.util.List;
import transactions.Voucher;

public class Customer {
	private int custID;
	private int custPoints;
	private String custName;
	private String creditCard;
	private List<String> allergens;
	private List<Voucher> vouchers;
	
	public Customer(int custID)	{
		this.custID = custID;
	}
	
	public Customer(int custID, int custPoints, String custName, String creditCard, List<String> allergens, List<Voucher> vouchers)	{
		this.custID = custID;
		this.custPoints = custPoints;
		this.custName = custName;
		this.creditCard = creditCard;
		this.allergens = allergens;
		this.vouchers = vouchers;
	}
	
	public Customer() {
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
	
	public List<String> getAllergens()	{
		return allergens;
	}
	
	public void setAllergens(List<String> allergens)	{
		this.allergens = allergens;
	}
	
	public List<Voucher> getVouchers()	{
		return vouchers;
	}
	
	public void setVouchers(List<Voucher> vouchers)	{
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