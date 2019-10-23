package gc.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import gc.bean.Student;
import gc.mapper.StudentMapper;

public class TestWithAnnotation {

	public static void main(String[] args) {
		TestWithAnnotation annotation = new TestWithAnnotation();
		int id = 1;
		annotation.deleteOne(id);
		annotation.selectOne(id);
		annotation.selectAll();
		annotation.insertOne(id);
		annotation.updateOne(id);
	}
	public Student selectOne(int id) {
		SqlSessionFactory factory = MysqlSessionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		StudentMapper mapper = session.getMapper(StudentMapper.class);
		Student student = mapper.selectOne2(id);
		System.out.println(student);
		session.close();
		return student;
	}
	public void selectAll() {
		SqlSessionFactory factory = MysqlSessionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		StudentMapper mapper = session.getMapper(StudentMapper.class);
		List<Student> students = mapper.selectAll2();
		System.out.println(students);
		session.close();
	}
	
	public void insertOne(int id) {
		SqlSessionFactory factory = MysqlSessionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		StudentMapper mapper = session.getMapper(StudentMapper.class);
		mapper.insertOne2(new Student(id, "insert"+id, 24));
		session.commit();
		session.close();
	}
	
	public void updateOne(int id) {
		SqlSessionFactory factory = MysqlSessionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		StudentMapper mapper = session.getMapper(StudentMapper.class);
		Student student = selectOne(id);
		student.setName("update"+id);
		mapper.updateOne2(student);
		session.commit();
		session.close();
	}
	public void deleteOne(int id) {
		SqlSessionFactory factory = MysqlSessionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		StudentMapper mapper = session.getMapper(StudentMapper.class);
		Student student = selectOne(id);
		mapper.deleteOne2(student);
		session.commit();
		session.close();
	}

}
