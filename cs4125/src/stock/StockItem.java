package stock;

//Author: Michael
public class StockItem extends OrderItem
{
	private double price;
	private String useBy;
	
	public StockItem(int itemID, Product prod, int qty, String useBy)
	{
		super(itemID, prod, qty);
		this.price = prod.getCostPrice() * 2;
		this.useBy = useBy;
	}
		
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getUseBy() {
		return useBy;
	}
}	