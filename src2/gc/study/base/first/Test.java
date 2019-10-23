package gc.study.base.first;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import gc.study.base.first.bean.Department;
import gc.study.base.first.bean.Employee;
import gc.study.base.first.bean.dao.DepartmentMapper;
import gc.study.base.first.bean.dao.EmployeeMapper;
import gc.study.base.first.bean.dao.EmployeeMapperAnnotation;

public class Test {

	public static void main(String[] args) throws IOException {
		test();
		testInterface();
		testAnnotation();
		testSet();
		testResultMap();
	}
	/*
	 * 1.根据xml配置文件（全局配置文件）创建一个SqlSessionFactory对象
	 * 2.sql映射文件中标配置了每一个sql以及sql的封装规则
	 * 3.将sql映射文件注册在全局配置文件中
	 * 4.写代码
	 * 		1) 根据全局配置文件得到SqlSessionFactory
	 * 		2) 使用sqlSessionFactory，获取sqlSession对象，使用它来进行crud，用完关闭
	 * 		3) 使用sql的唯一标识告诉mybatis执行哪个sql
	 */
	private static void test() throws IOException {
		System.out.println("start test");
		String resource = "gc/study/base/first/mybatis.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
		
		SqlSession session = factory.openSession();
		//使用sql映射文件中的namespace.id,这样可以保证唯一性
		//这里可以映射的前提是查询出来的字段名和对象中的字段名一致，否则无法映射相应的字段，或者在编写sql语句时指定别名来对象对象中的字段
		Employee employee = session.selectOne("first.selectEmp", 1);
		System.out.println("xml: "+employee);
		session.close();
	}
	/*
	 * 接口式编程：
	 * 	原生：一个
	 * 	mybatis：接口中定义方法，xml中实现方法，通过反射的方式。（通过namespace+id匹配对象）
	 * 2.sqlSession代表和数据库的一次会话，用完需要关闭
	 * 3.sqlSession和connection一样都是非线程安全。每次使用都应该去获取新的对象
	 * 4.mapper接口没有实现类，但是mybatis会为这个接口生成一个代理对象
	 * 5.两个重要的配置文件
	 * 		mybatis的全局配置文件包含数据库连接池信息，事物管理器，系统运行信息
	 * 		sql映射文件，保存了每一个sql语句的映射信息
	 */
	private static void testInterface() throws IOException {
		String resource = "gc/study/base/first/mybatis.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
		
		SqlSession session = factory.openSession();
		//mybatis会为接口创建代理对象，代理对象去执行crud操作
		EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
		Employee employee = mapper.getEmpById(1);
		System.out.println("mapper:" + employee);
		
		employee = mapper.getEmpByIdAndName(1, "dadada");
		System.out.println("mapper2:"+employee);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", 1);
		map.put("lastName", "dadada");
		employee = mapper.getEmpByIdAndName(1, "dadada");
		System.out.println("mapper3:"+employee);
		session.close();
	}
	
	private static void testAnnotation() throws IOException {
		String resource = "gc/study/base/first/mybatis.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
		
		SqlSession session = factory.openSession();
		EmployeeMapperAnnotation mapper = session.getMapper(EmployeeMapperAnnotation.class);
		Employee employee = mapper.getEmpById(1);
		System.out.println("annotation:" + employee);
		session.close();
	}
	
	private static void testSet() throws IOException {
		String resource = "gc/study/base/first/mybatis.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
		
		SqlSession session = factory.openSession();
		EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
		List<Employee> employees = mapper.getEmpByLastName("%gc%");
		System.out.println("list:" + employees);
		
		Map<String, Object> map = mapper.getEmpByIdReturnMap(1);
		System.out.println(map);
		
		Map<Integer, Employee> map2 = mapper.getEmpByLastNameReturnMap("%gc%");
		System.out.println(map2);
		session.close();
	}
	
	private static void testResultMap() throws IOException {
		String resource = "gc/study/base/first/mybatis.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
		
		SqlSession session = factory.openSession();
		EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
		Employee e = mapper.getEmpById_map(1);
		System.out.println("resultMap1:"+e);
		
		e = mapper.getEmpAndDept(1);
		System.out.println("empanddept:" + e);
		e = mapper.getEmpByIdStep(1);
		//这里测试懒加载，打印name时不回加载dept信息
		System.out.println("getEmpByIdStep:" + e.getLastName());
		System.out.println("getEmpByIdStep:" + e);
		
		DepartmentMapper deptMapper = session.getMapper(DepartmentMapper.class);
		Department dept = deptMapper.getDeptByIdPlus(1);
		System.out.println(dept);
		System.out.println(dept.getEmps());
		
		dept = deptMapper.getDeptStep(1);
		System.out.println("dept:"+dept.getDepartmentName());
		System.out.println("e:"+dept.getEmps());
		session.close();
	}

}
