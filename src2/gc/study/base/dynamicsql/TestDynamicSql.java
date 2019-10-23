package gc.study.base.dynamicsql;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import gc.study.base.dynamicsql.bean.Department;
import gc.study.base.dynamicsql.bean.Employee;
import gc.study.base.dynamicsql.dao.EmployeemapperDynamicSql;

public class TestDynamicSql {

	public static void main(String[] args) throws IOException {
		test();
	}
	/*
	 * 测试增删改
	 * 	mybatis允许增删改直接定义以下类型的返回值
	 * 		Integer,Long,Boolean
	 */
	private static void test() throws IOException {
		System.out.println("start test");
		String resource = "gc/study/base/dynamicsql/mybatis.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = factory.openSession();
		
		EmployeemapperDynamicSql mapper = session.getMapper(EmployeemapperDynamicSql.class);
		Employee e = new Employee(4,"gc","958917036","1",null);
		List<Employee> es = mapper.getEmpsByConditionIf(e);
		System.out.println(es);
		
		e = new Employee(null,null,null,null,null);
		es = mapper.getEmpsByConditionChoose(e);
		System.out.println("choose:"+es);
		
		e = new Employee(4,"asdf",null,null,null);
		mapper.updateEmp(e);
		
		List<Employee> es2 = mapper.getEmpsForeach(Arrays.asList(1,2,3,4,5));
		System.out.println("foreach:"+es2);
		
		ArrayList emps = new ArrayList();
		emps.add(new Employee(44,"gc","958917036","1",new Department(1,"dname")));
		emps.add(new Employee(54,"gc","958917036","1",new Department(2,"dname")));
		mapper.addEmps(emps);
		
		e.setLastName("g");
		es = mapper.getEmpsTestInternalParamters(e);
		System.out.println("essize:"+es.size()+" ,es:"+es);
		session.commit();
		session.close();
	}

}
