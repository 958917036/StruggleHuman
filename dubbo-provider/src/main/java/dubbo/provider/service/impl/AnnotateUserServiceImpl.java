package dubbo.provider.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;

import dubbo.provider.bean.User;
import dubbo.provider.service.UserService;

@Service(timeout = 1000)
public class AnnotateUserServiceImpl implements UserService{

	public AnnotateUserServiceImpl() {
		System.out.println("UserServiceImpl inited");
	}

	public String sayHello(String name) {
		// TODO Auto-generated method stub
		return "Annotate hello " + name;
	}

	public List<User> getUser() {
		List<User> list = new ArrayList<User>();
		User u1 = new User("gc",25);
		User u2 = new User("zj",24);
		list.add(u1);
		list.add(u2);
		return list;
	}

}
