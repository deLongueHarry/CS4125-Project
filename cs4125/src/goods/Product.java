//change package name if needed
package goods;

public class Product {

	int productID, minimumOrder;
	double costPrice;
	String productName, productType, company;
	
	public Product(int ID, String name, String type, double costPrice, String company, int minimumOrder) {
		
		this.productID = ID;
		this.productName = name;
		this.productType = type;
		this.costPrice = costPrice;
		this.company = company;	
		this.minimumOrder = minimumOrder;
	}
	
	public int getProductID() {
		return productID;
	}
	
	public void setProductID(int ID) {
		this.productID = ID;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String name) {
		this.productName = name;
	}
	
	public String getType() {
		return productType;
	}
	
	public void setType(String type) {
		this.productType = type;
	}
	
	public double getPrice() {
		return costPrice;
	}
	
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	public int getMinOrder() {
		return minimumOrder;
	}

	public void setMinOrder(int minimumOrder) {
		this.minimumOrder = minimumOrder;
	}
}