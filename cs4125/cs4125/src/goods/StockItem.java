package goods;

public class StockItem 
{
	private int stockItmID;
	private Product prod;
	private int qty;
	private double price;
	private String useBy;
	
	double markup = 1.15;
	
	public StockItem(int stockItmID, Product prod, int qty, String useBy)
	{
		this.stockItmID = stockItmID;
		this.prod = prod;
		this.qty = qty;
		this.useBy = useBy;
		
		price = prod.getCostPrice() * markup;
		price -= (price % 1) + 0.01;
	}
	
	public int getItmID() {
		return stockItmID;
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
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getUseBy() {
		return useBy;
	}
}	
