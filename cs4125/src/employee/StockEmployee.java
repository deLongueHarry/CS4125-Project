package employee;

public class StockEmployee implements Employee {

	private int ID;
	private String name;
	private String password;
	private String address;
	private String phoneNo;

	public StockEmployee() {
		
	}
	
	public StockEmployee(int ID, String name, String password) {
		
		setID(ID);
		setName(name);
		setPassword(password);
	}
	
	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
}