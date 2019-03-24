package dubbo.consumer.init.api;

import java.io.IOException;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;

import dubbo.provider.service.UserService;

public class TestConsumerApi {

	static ReferenceConfig<UserService> reference;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		init();
	}
	
	public static void init() {
		
		ApplicationConfig application = new ApplicationConfig();
		application.setName("dubbo_consumer_api");
		
		RegistryConfig registry = new RegistryConfig();
		registry.setAddress("127.0.0.1:2181");
		registry.setProtocol("zookeeper");

		reference = new ReferenceConfig<UserService>();
		reference.setApplication(application);
		reference.setRegistry(registry);
		reference.setInterface(UserService.class);
		reference.setVersion("1.0.0");
		reference.setTimeout(1000);
		reference.setCheck(false);
		test();
		
	}
	
	private static void test() {
		System.out.println("start get service");
		if(reference != null) {
			UserService userService = reference.get();
			System.out.println(userService.sayHello("api"));
			
		}
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
