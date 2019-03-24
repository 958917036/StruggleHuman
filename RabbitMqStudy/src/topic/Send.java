package topic;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

//topic与direct的区别是，topic绑定的routingKey支持通配符
public class Send {
	private static final String EXCHANGE_NAME = "topic_logs";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		foo();
	}
	
	private static void foo() {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			channel.exchangeDeclare(EXCHANGE_NAME, "topic");
			for (int i = 0; i < 3; i++) {
				String serverity = "info.warn"+i;
				String message = "info.warn"+i;
				channel.basicPublish(EXCHANGE_NAME, serverity, null, message.getBytes());
				System.out.println("Send:"+message);
			}
			channel.close();
			connection.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
