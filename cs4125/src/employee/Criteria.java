package employee;

import java.util.List;

//Implementing Criteria Pattern
public interface Criteria {
	
	public List<Employee> meetCriteria(List<Employee> employees);
}
