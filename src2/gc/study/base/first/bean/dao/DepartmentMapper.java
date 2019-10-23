package gc.study.base.first.bean.dao;

import java.util.List;

import gc.study.base.first.bean.Department;
import gc.study.base.first.bean.Employee;

public interface DepartmentMapper {
	
	public Department getDeptById(Integer id);
	
	public Department getDeptByIdPlus(Integer id);

	public Department getDeptStep(Integer id);
	
}
