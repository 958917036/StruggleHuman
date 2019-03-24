package dubbo.provider.init.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;

@Configuration
public class AnnotateConfiguration {

	@Bean
	public ApplicationConfig applicationConfig() {
		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setName("dubbo_provider_annotation");
		return applicationConfig;
	}
	
	@Bean
	public RegistryConfig registryConfig() {
		RegistryConfig registryConfig  = new RegistryConfig();
		registryConfig.setAddress("127.0.0.1");
		registryConfig.setProtocol("zookeeper");
		return registryConfig;
	}

}
