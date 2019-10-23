package gc.study.base.crud.dao;

import gc.study.base.crud.bean.Employee;

public interface EmployeeMapper {

	public Employee getEmpById(Integer id);
	
	public void addEmp(Employee employee);
	
	public void updateEmp(Employee employee);
	
	public void deleteEmp(Integer id);
	
	//定义数据库操作的返回类型，这里定义为boolean，则操作成功返回true，操作失败返回false
	public boolean deleteEmp(int id);
}
