package base;

import java.io.UnsupportedEncodingException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Recv {

	private final static String QUEUE_NAME = "hello";
	
	public static void main(String[] args) {
		foo();
	}
	
	private static void foo() {
		//向本地localhost建立一个和物理连接
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try {
			//tcp物理连接的一个抽象，关注协议版本和认证
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			//这里同样声明queue，因为Producer可能先于Consumer运行，所以需要保证queue存在
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			//定义一个消费者实现类来处理消息的上报
			DefaultConsumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
					String message = new String(body,"UTF-8");
					System.out.println("Received:"+message);
				}
			};
			channel.basicConsume(QUEUE_NAME, true, consumer);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
