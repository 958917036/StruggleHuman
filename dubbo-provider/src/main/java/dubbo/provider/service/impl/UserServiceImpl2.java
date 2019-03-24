package dubbo.provider.service.impl;

import java.util.ArrayList;
import java.util.List;

import dubbo.provider.bean.User;
import dubbo.provider.service.UserService;

public class UserServiceImpl2 implements UserService {

	public UserServiceImpl2() {
		System.out.println("UserServiceImpl inited");
	}
	
	@Override
	public String sayHello(String name) {
		return "hello " + name;
	}

	@Override
	public List<User> getUser() {
		List<User> list = new ArrayList<User>();
		User u1 = new User("gc",25);
		User u2 = new User("user service 2",24);
		list.add(u1);
		list.add(u2);
		System.out.println("user service 2");
		return list;
	}

}
