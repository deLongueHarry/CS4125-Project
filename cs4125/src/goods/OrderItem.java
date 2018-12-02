package goods;

public class OrderItem 
{
	private int orderItmID;
	private Product prod;
	private int qty;
	private double price;
	
	public OrderItem(int orderItmID, Product prod, int qty)
	{
		this.orderItmID = orderItmID;
		this.prod = prod;
		this.qty = qty * prod.getMinimumOrder();
		this.price = prod.getCostPrice();
	}
	
	public int getItmID() {
		return orderItmID;
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