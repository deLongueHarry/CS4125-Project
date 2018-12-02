package stock;

//Author: Michael
public class OrderItem 
{
	private int itemID;
	private Product prod;
	private int qty;
	private double price;
	
	public OrderItem(int itemID, Product prod, int qty)
	{
		this.itemID = itemID;
		this.prod = prod;
		this.qty = qty * prod.getMinimumOrder();
		this.price = prod.getCostPrice();
	}
	
	public int getItmID() {
		return itemID;
	}
	
	public Product getProduct() {
		return prod;
	}

	public void setProduct(Product prod) {
		this.prod = prod;
	}
	
	public int getQty() {
		return qty;
	}
	
	public void setQty(int qty) {
		this.qty = qty;
	}
	
	public double getPrice() {
		return price;
	}
}	