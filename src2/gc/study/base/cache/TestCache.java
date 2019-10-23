package gc.study.base.cache;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import gc.study.base.cache.dao.EmployeeMapper;
import gc.study.base.cache.bean.Employee;

public class TestCache {

	public static void main(String[] args) throws IOException {
		testFirstLevelCache();
		testSecondLevelCache();
	}
	/*
	 * 两级缓存
	 * 一级缓存：(本地缓存)
	 * 		与数据库同一次会话期间查询到的数据会放在本地缓存中，生命周期 为一个SqlSession
	 * 		一级缓存有效情况：
	 * 		一级缓存失效的情况：
	 * 			sqlSession不同
	 * 			sqlSession相同，但是查询条件不同
	 * 			sqlSession相同，条件相同，但是两次查询之间执行了增删改操作
	 * 			sqlSession相同，条件相同，但是手动清空了查询缓存
	 * 二级缓存：（全局缓存），基于namespace级别的缓存，一个namespace对应一个二级缓存
	 * 	工作机制：
	 * 	一个会话，查询一条数据，这个数据会被放到一级缓存中
	 * 	只有会话关闭或者提交，一级缓存中的数据才会被保存在二级缓存中，否则二级缓存看不到这些数据
	 * 	不同namespace查询的数据会放在自己对应的二级缓存中
	 * 	使用：
	 * 		开启全局二级缓存（默认开启）:<setting name="cacheEnabled" value="true"/>
	 * 		在具体的mapper.xml中配置使用二级缓存<cache> </cache>
	 * 		pojo对象需要实现序列化接口
	 * 和缓存有关的设置和属性：
	 * 		CacheEnabled=true/false  开启，关闭二级缓存，全局配置（如果需要使用缓存，全局配置需要先打开）
	 * 		select标签中的useCache=true/false 开启，关闭具体查询操作的二级缓存
	 * 		每个增删改标签的：flushCache=true/false，（默认true，查询标签中默认为false）每次增删改执行完之后就会清空缓存
	 * 			（所以两个相同的查询之间加一个增删改操作都会导致缓存失效，重新执行sql查询）
	 * 		sqlSession.clearCache()  清除一级缓存
	 * 		localCacheScope 本地缓存作用域，SESSION/STATEMENT。
	 * 			SESSION当前会话的所有数据保存在会话缓存中作为一个一级缓存
	 * 			STATEMENT：可以禁用一级缓存
	 * 缓存的使用顺序：
	 * 	 二级缓存
	 * 	一级缓存
	 * 	数据库查询
	 * 		
	 */
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
	
	public static void testSecondLevelCache() throws IOException {
		System.out.println("-------start test second level cache----------");
		String resource = "gc/study/base/cache/mybatis.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session1 = factory.openSession();
		SqlSession session2 = factory.openSession();
		
		EmployeeMapper mapper1 = session1.getMapper(EmployeeMapper.class);
		EmployeeMapper mapper2 = session2.getMapper(EmployeeMapper.class);
		
		Employee e1 = mapper1.getEmpById(4);
		session1.commit();
		Employee e2 = mapper2.getEmpById(4);
		
		session1.close();
		session2.close();
	}
	
}
