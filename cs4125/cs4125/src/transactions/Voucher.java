package transactions;

public class Voucher {
	
	private int voucherNo;
	private double amount;
	
	public Voucher(int voucherNo, double amount)	{
		this.voucherNo = voucherNo;
		this.amount = amount;
	}
	
	public int getVoucherNo()	{
		return voucherNo;
	}
	
	public void setVoucherNo(int voucherNo)	{
		this.voucherNo = voucherNo;
	}
	
	public double getAmount()	{
		return amount;
	}
	
	public void setAmount(double amount)	{
		this.amount = amount;
	}
}
