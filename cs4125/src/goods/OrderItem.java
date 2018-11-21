package goods;

public class OrderItem 
{
	private int orderItmID;
	private Product prod;
	private int qty;
	private double price;
	
	public OrderItem(int orderItmID, Product prod, int qty, double price)
	{
		this.prod = prod;
		this.qty = qty;
		this.price = price;
	}
	
	public int getOrderItmID()	{
		return orderItmID;
	}
	
	public void setOrderItmID(int orderItmID)	{
		this.orderItmID = orderItmID;
	}
	
	public Product getProduct() {
		return prod;
	}

	public void setProduct(Product prod) {
		this.prod = prod;
	}
	
	public int getqty() {
		return qty;
	}
	
	public void setqty(int qty) {
		this.qty = qty;
	}
	
	public double getPrice() {
		return price;
	}
}	