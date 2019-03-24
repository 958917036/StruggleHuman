package workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send1 {

	private static final String QUEUE_NAME = "workqueue";
	public static void main(String[] args) {
		foo();
	}

	private static void foo() {
		try{
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			boolean durable = true;  //指定消息是否需要持久化，避免rebbitMq挂掉之后消息丢失
			channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
			String dots = "";
			for(int i=0; i < 10; i++) {
				dots += ".";
				String message = "helloworld" + dots + dots.length();
				channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
				System.out.println("Send:"+message);
			}
			channel.close();
			connection.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
