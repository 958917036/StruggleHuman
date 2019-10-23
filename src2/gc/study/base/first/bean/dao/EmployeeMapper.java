package gc.study.base.first.bean.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import gc.study.base.first.bean.Employee;

//
public interface EmployeeMapper {

	public Employee getEmpById(Integer id);
	//指定命名参数，用于sql中获取参数#{id}，mybatis对于多参情况会把参数封装到map中，@Param指定了map中对应的key，这样sql中#{key}就可以找到相应的value了
	public Employee getEmpByIdAndName(@Param("id")Integer id,@Param("lastName")String lastName);

	public Employee getEmpByMap(Map<String,Object> map);
	
	public List<Employee> getEmpByLastName(String lastName);
	
	//返回一条记录map，key是列名，value就是列对应的值
	public Map<String,Object> getEmpByIdReturnMap(int id);
	
	//多条记录封装一个map，键是这条记录的主键，值是记录封装后的javaBean
	//MapKey告诉mybatis封装这个map的时候使用哪个属性作为map的key
	@MapKey("id")
	public Map<Integer,Employee> getEmpByLastNameReturnMap(String lastName);
	
	//下面介绍resultMap
	public Employee getEmpById_map(Integer id); 
	
	public Employee getEmpAndDept(Integer id);
	
	public Employee getEmpByIdStep(Integer id);
	
	public List<Employee> getEmpsByDeptId(Integer id);

}
