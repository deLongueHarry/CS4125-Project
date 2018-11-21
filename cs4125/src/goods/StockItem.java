package goods;

public class StockItem
{
	private int stockItmID;
	private Product prod;
	private int qty;
	private double price;
	private String useBy;
	
	public StockItem(int stockItmID, Product prod, int qty, double price, String useBy)
	{
		this.stockItmID = stockItmID;
		this.prod = prod;
		this.qty = qty;
		this.price = price;
		this.useBy = useBy;
	}

	public int getStockItmID()	{
		return stockItmID;
	}
	
	public void setStockItmID(int stockItmID)	{
		this.stockItmID = stockItmID;
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
	
	public void setPrice(double price)	{
		this.price = price;
	}
	
	public String getUseBy() {
		return useBy;
	}

	public void setUseBy(String useBy)	{
		this.useBy = useBy;
	}
}