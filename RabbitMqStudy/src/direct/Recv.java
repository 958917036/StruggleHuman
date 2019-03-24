package direct;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class Recv {
	private static final String EXCHANGE_NAME = "direct_logs";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		args = new String[]{"info1","info2"};
		foo(args);
	}

	private static void foo(String[] args) {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			//声明一个直连的exchange，direct的意思是根据routing key进行转发
			channel.exchangeDeclare(EXCHANGE_NAME, "direct");
			String queueName = channel.queueDeclare().getQueue();
			
			//这里将同一个queue绑定了info1，info2两个routing key(binding key),所以可以收到info1，info2的消息
			for(String severity : args) {
				channel.queueBind(queueName, EXCHANGE_NAME, severity);
				System.out.println("routingKey:"+severity);
			}
			Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
						byte[] body) throws IOException {
					String message = new String(body,"UTF-8");
					System.out.println(queueName+" Recv "+message);
				}
			};
			channel.basicConsume(queueName, true, consumer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
