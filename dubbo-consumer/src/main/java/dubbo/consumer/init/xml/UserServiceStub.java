package dubbo.consumer.init.xml;

import java.util.List;

import dubbo.provider.bean.User;
import dubbo.provider.service.UserService;

/**
 * 本地存根对象，调用远程代理对象前会先调用本地存根，可以用来做条件，参数判断
 * @author gc
 *
 */
public class UserServiceStub implements UserService {

	private final UserService userService;
	
	/** 传入的是远程userService远程的代理对象，由dubbo自动传入
	 * @param userService
	 */
	public UserServiceStub(UserService userService) {
		super();
		this.userService = userService;
	}

	@Override
	public String sayHello(String name) {
		return null;
	}

	@Override
	public List<User> getUser() {
		System.out.println("调用本地存根");
		return userService.getUser();
	}
	
}
