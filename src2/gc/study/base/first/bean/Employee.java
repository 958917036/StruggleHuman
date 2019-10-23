package gc.study.base.first.bean;

import org.apache.ibatis.type.Alias;
//可以使用注解起别名
//@Alias("emp")
public class Employee {

	private Integer id;
	private String lastName;
	private String email;
	private String gender;
	private Department dept;
	
	public Department getDept() {
		return dept;
	}
	public void setDept(Department dmp) {
		this.dept = dmp;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", lastName=" + lastName + ", email=" + email + ", gender=" + gender + ", dmp="
				+ dept + "]";
	}

}
