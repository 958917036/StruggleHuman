package dubbo.provider.init.xml;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestProviderXml {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		init();
	}
	
	public static void init() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/applicationProvider.xml");
		context.start();
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		context.close();
	}

}
