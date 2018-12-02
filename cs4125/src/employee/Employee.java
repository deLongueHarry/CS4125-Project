package employee;

public class Employee {
	
	private int ID;
	private String type;
	private String name;
	private String password;
	private String address;
	private String phoneNo;
	
	public Employee() {}
	
	public Employee(int ID, String type, String name, String password, String address, String phoneNo)
	{
		this.ID = ID;
		this.type = type;
		this.name = name;
		this.password = password;
		this.address = address;
		this.phoneNo = phoneNo;
	}
	
	public int getID()	{
		return ID;
	}
	
	public void setID(int ID)	{
		this.ID = ID;
	}
	
	public String getType()	{
		return type;
	}
	
	public void setType(String type)	{
		this.type = type;
	}
	
	public String getName()	{
		return name;
	}
	
	public void setName(String name)	{
		this.name = name;
	}
	
	public String getPassword()	{
		return password;
	}
	
	public void setPassword(String password)	{
		this.password = password;
	}
	
	public String getAddress()	{
		return address;
	}
	
	public void setAddress(String address)	{
		this.address = address;
	}

	public String getPhoneNo()	{
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo)	{
		this.phoneNo = phoneNo;
	}
}