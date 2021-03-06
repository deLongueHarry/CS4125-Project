package employee;

import java.util.ArrayList;
import java.util.List;

public class CriteriaStockEmployee implements Criteria	{

	@Override
	public List<Employee> meetCriteria(List<Employee> employees) {
		List<Employee> stockEmployees = new ArrayList<>();
		
		for (Employee emp : employees)	{
			if (emp.getType().toLowerCase() == "stock employee")	{
				stockEmployees.add(emp);
			}
		}		
		return stockEmployees;
	}

}
