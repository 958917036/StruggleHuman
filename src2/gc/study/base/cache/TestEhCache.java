package gc.study.base.cache;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import gc.study.base.cache.dao.EmployeeMapper;
import gc.study.base.cache.bean.Employee;
public class TestEhCache {

	public static void main(String[] args) throws IOException {
		//待调试实现
		//testFirstLevelCache();
	}
	private static void testFirstLevelCache() throws IOException {
		System.out.println("start test");
		String resource = "gc/study/base/cache/mybatis.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = factory.openSession();
		EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
		Employee e1 = mapper.getEmpById(1);
		System.out.println("e:"+e1);
		//第二次查询不会再发sql语句，因为本地有缓存（同一个factory.openSession()中）
		Employee e2 = mapper.getEmpById(1);
		System.out.println("e:"+e2+" e1==e2:"+(e1==e2));
		System.out.println("1  e1==e2:"+(e1==e2));
		
		e1 = mapper.getEmpByGender("0");
		e2 = mapper.getEmpByGender("0");
		System.out.println("2  e1==e2:"+(e1==e2));
		session.commit();
		//session关闭后，一级缓存自动失效
		session.close();
	}
	

	
}
