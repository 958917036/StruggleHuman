package gc.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import gc.bean.Student;

public class TestWithXml {

	public static void main(String[] args) {
		int id = 100;
		TestWithXml xml = new TestWithXml();
		xml.deleteOne(id);
		xml.selectOne(id);
		xml.insertOne(id);
		xml.updateOne(id);
	}
	public Student selectOne(int id) {
		SqlSessionFactory sqlSessionFactory = null;
		String resource = "mybatis_config.xml";
		InputStream in;
		SqlSession session = null;
		try {
			in = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
			session = sqlSessionFactory.openSession();
			Student s = session.selectOne("gc.mapper.StudentMapper.selectOne", id);
			System.out.println("selectOne:"+s);
			return s;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally{
			if(session != null) {
				session.close();
			}
		}
	}
	public void selectAll() {
		SqlSessionFactory sqlSessionFactory = null;
		SqlSession session = null;
		try {
			sqlSessionFactory = MysqlSessionFactory.getSqlSessionFactory();
			session = sqlSessionFactory.openSession();
			
			List<Student> s = session.selectList("gc.mapper.StudentMapper.selectStudent");
			System.out.println("selectAll:"+s);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}
	public void insertOne(int id) {
		SqlSessionFactory sqlSessionFactory = null;
		SqlSession session = null;
		try {
			sqlSessionFactory = MysqlSessionFactory.getSqlSessionFactory();
			session = sqlSessionFactory.openSession();
			
			session.insert("gc.mapper.StudentMapper.insertOne", new Student(id,"insert_xml_"+id,30));
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}
	
	public void deleteOne(int id) {
		SqlSessionFactory factory = MysqlSessionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		Student student = selectOne(id);
		session.delete("gc.mapper.StudentMapper.deleteOne", student);
		session.commit();
		session.close();
	}
	
	public void updateOne(int id) {
		SqlSessionFactory factory = MysqlSessionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		Student student = selectOne(id);
		student.setName("update_"+id);
		session.update("gc.mapper.StudentMapper.updateOne", student);
		session.commit();
		session.close();
	}
}
