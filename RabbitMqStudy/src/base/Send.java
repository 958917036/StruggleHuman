package base;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

	private final static String QUEUE_NAME = "hello";
	
	public static void main(String[] args) {
		foo();
	}

	private static void foo() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = null;
		try {
			connection = factory.newConnection();
			Channel channel = connection.createChannel();
			//声明一个我们将要发送数据进去的queue，然后向其发送数据。queue的声明是幂等的-只有不存在的话才会实际去创建
			//数据内容是byte数组
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			for (int i = 0; i < 5; i++) {
				String message = "hello world"+i;
				channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
				System.out.println("Sent:"+message);
			}
			channel.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
