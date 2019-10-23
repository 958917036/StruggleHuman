package gc.study.base.crud;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.type.Alias;

import gc.study.base.crud.bean.Employee;
import gc.study.base.crud.dao.EmployeeMapper;

public class TestCRUD {

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
		String resource = "gc/study/base/crud/mybatis.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
		//这样获取到的session不会自动提交
		SqlSession session = factory.openSession();
		EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
		Employee employee = mapper.getEmpById(1);
		System.out.println("select:"+employee);
		employee.setLastName("dadada");
		mapper.updateEmp(employee);
		//这里id为自增，所以设置为null即可，显示设置值也不能生效
		Employee employee2 = new Employee(null, "gc", "958917036", "1");
		mapper.addEmp(employee2);
		//xml映射文件中配置了useGeneratedKeys为true，所以对象中id值不为null，为数据库生成的自增主键值
		System.out.println("add:"+employee2);
		mapper.deleteEmp(3);
		session.commit();
		session.close();
	}

}
