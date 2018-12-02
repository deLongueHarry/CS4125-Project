package goods;

import java.util.List;

public class Product {

	private int productID;
	private String productName;
	private String productType;
	private String company;
	private double costPrice;
	private int minimumOrder;
	private List<String> allergens;
	
	public Product(int ID, String name, String type, String comp, double costPrice, int minimumOrder, List<String> allergens) {
		
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
	
	public List<String> getAllergens()	{
		return allergens;
	}
	
	public void setAllergens(List<String> allergens)	{
		this.allergens = allergens;
	}
	
}