package gc.study.base.cache.dao;

import gc.study.base.cache.bean.Employee;

public interface EmployeeMapper {

	public Employee getEmpById(Integer id);
	
	public Employee getEmpByGender(String gender);
	
	public void addEmp(Employee employee);
	
	public void updateEmp(Employee employee);
	
	public void deleteEmp(Integer id);
	
	//定义数据库操作的返回类型，这里定义为boolean，则操作成功返回true，操作失败返回false
	public boolean deleteEmp(int id);
}
