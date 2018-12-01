package goods;

import java.util.ArrayList;

public class Product {

	int productID;
	String productName, productType, company;
	double costPrice;
	int minimumOrder;
	ArrayList<String> allergens;
	
	public Product(int ID, String name, String type, String comp, double costPrice, int minimumOrder, ArrayList<String> allergens) {
		
		this.productID = ID;
		this.productName = name;
		this.productType = type;
		this.company = comp;
		this.costPrice = costPrice;
		this.minimumOrder = minimumOrder;
		this.allergens = allergens;
	}
	
	public int getProductID() {
		return productID;
	}
	
	public void setProductID(int ID) {
		this.productID = ID;
	}
	
	public int getMinimumOrder()	{
		return minimumOrder;
	}
	
	public void setMinimumOrder(int minimumOrder)	{
		this.minimumOrder = minimumOrder;
	}
	
	public double getCostPrice()	{
		return costPrice;
	}
	
	public void setCostPrice(double costPrice)	{
		this.costPrice = costPrice;
	}
	
	public String getType() {
		return productType;
	}
	
	public void setType(String type) {
		this.productType = type;
	}
	
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	public String getProductName()	{
		return productName;
	}
	
	public void setProductName(String productName)	{
		this.productName = productName;
	}
	
	public ArrayList<String> getAllergens()	{
		return allergens;
	}
	
	public void setAllergens(ArrayList<String> allergens)	{
		this.allergens = allergens;
	}
}
