package gc.study.base.dynamicsql.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import gc.study.base.dynamicsql.bean.Employee;

public interface EmployeemapperDynamicSql {
	public List<Employee> getEmpsByConditionIf(Employee e);
	
	public List<Employee> getEmpsByConditionChoose(Employee e);
	
	public void updateEmp(Employee e);
	
	public List<Employee> getEmpsForeach(@Param("ids") List list);
	
	public void addEmps(@Param("emps") List<Employee> emps);
	
	public List<Employee> getEmpsTestInternalParamters(Employee e);
}
