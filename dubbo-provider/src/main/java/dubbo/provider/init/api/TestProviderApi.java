package dubbo.provider.init.api;

import java.io.IOException;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;

import dubbo.provider.service.UserService;
import dubbo.provider.service.impl.UserServiceImpl;

public class TestProviderApi {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		init();
	}
	
	public static void init() {
		ApplicationConfig application = new ApplicationConfig();
		application.setName("dubbo_provider_api");
		
		RegistryConfig registry = new RegistryConfig();
		registry.setAddress("127.0.0.1:2181");
		registry.setProtocol("zookeeper");
		
		ProtocolConfig protocol = new ProtocolConfig();
		protocol.setName("dubbo");
		protocol.setPort(12345);
		protocol.setThreads(100);
		
		UserService userService = new UserServiceImpl();
		
		ServiceConfig<UserService> service = new ServiceConfig<UserService>();
		service.setApplication(application);
		service.setRegistry(registry);
		service.setProtocol(protocol);
		service.setInterface(UserService.class);
		service.setRef(userService);
		service.setVersion("1.0.0");
		service.setTimeout(1);
		
		System.out.println("provider end1");
		service.export();
		System.out.println("provider end2");
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
