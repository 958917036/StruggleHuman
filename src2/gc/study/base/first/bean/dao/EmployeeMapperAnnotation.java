package gc.study.base.first.bean.dao;

import org.apache.ibatis.annotations.Select;

import gc.study.base.first.bean.Employee;

//
public interface EmployeeMapperAnnotation {
	
	@Select("select * from tbl_employee where id=#{id}")
	public Employee getEmpById(Integer id);

}
