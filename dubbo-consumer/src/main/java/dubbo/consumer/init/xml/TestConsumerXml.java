package dubbo.consumer.init.xml;

import java.io.IOException;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import dubbo.provider.bean.User;
import dubbo.provider.service.UserService;

public class TestConsumerXml {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		init();
	}
	
	public static void init() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/applicationConsumer.xml");
		context.start();
		UserService userService = context.getBean("userService", UserService.class);
		String helloInfo = userService.sayHello("gu chuang");
		System.out.println(helloInfo);
		List<User> list = userService.getUser();
		System.out.println(list);
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		context.close();
	}
	
}
