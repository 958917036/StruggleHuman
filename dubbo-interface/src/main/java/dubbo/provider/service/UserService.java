package dubbo.provider.service;

import java.util.List;

import dubbo.provider.bean.User;

public interface UserService {
	String sayHello(String name);
	List<User> getUser();
}
