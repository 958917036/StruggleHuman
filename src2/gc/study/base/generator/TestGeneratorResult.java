package gc.study.base.generator;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import gc.study.base.generator.bean.Employee;
import gc.study.base.generator.bean.EmployeeExample;
import gc.study.base.generator.bean.EmployeeExample.Criteria;
import gc.study.base.generator.dao.EmployeeMapper;
/*
 * 测试mybatis逆向工程的正确性
 */
public class TestGeneratorResult {

	@Test
	public void test() throws IOException {
		System.out.println("start test");
		String resource = "gc/study/base/generator/mybatis.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = factory.openSession();
		EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
		Employee e = mapper.selectByPrimaryKey(1);
		System.out.println(e.getLastName());
		
		session.close();
	}
	
	@Test
	public void test2() throws IOException {
		String resource = "gc/study/base/generator/mybatis.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = factory.openSession();
		EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
		//查询所有数据
		List<Employee> es = mapper.selectByExample(null);
		for (Iterator iterator = es.iterator(); iterator.hasNext();) {
			Employee e = (Employee) iterator.next();
			System.out.println(e.getLastName());
		}
		System.out.println(es.size());
		//使用高级查询功能
		EmployeeExample example = new EmployeeExample();
		Criteria criteria = example.createCriteria();
		criteria.andLastNameEqualTo("gc");
		
		
		Criteria criteria2 = example.createCriteria();
		criteria2.andGenderEqualTo("3");
		example.or(criteria2);
		
		List<Employee> es2 = mapper.selectByExample(example);
		System.out.println(es2.size());
		
		session.close();
	}

}
