package gc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import gc.bean.Student;

public interface StudentMapper {
	@Select("select * from student where id = #{id}")
	public Student selectOne2(int id);
	
	@Select("select * from student")
	public List<Student> selectAll2();
	
	@Insert("insert into student(id,name,age) values(#{id},#{name},#{age})")
	public void insertOne2(Student student);
	
	@Update("update student set name=#{name} where id= #{id}")
	public void updateOne2(Student student);
	
	@Delete("delete from student where id = #{id}")
	public void deleteOne2(Student student);
}
