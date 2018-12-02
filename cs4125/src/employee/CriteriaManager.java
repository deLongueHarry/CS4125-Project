package employee;

import java.util.ArrayList;
import java.util.List;

public class CriteriaManager implements Criteria	{

	@Override
	public List<Employee> meetCriteria(List<Employee> employees) {
		List<Employee> managers = new ArrayList<>();
		
		for (Employee emp : employees)	{
			if (emp.getType().toLowerCase() == "manager")	{
				managers.add(emp);
			}
		}		
		return managers;
	}

}
