package topic;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class Recv {
	private static final String EXCHANGE_NAME = "topic_logs";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//args = new String[]{"#"};     //可以用来匹配所有的topic,#是指匹配0个或多个关键字
		//args = new String[]{"*"};     //只能匹配一个一个关键字，这里的关键字指的的以.分割的，如:send端topic为"info warn1"可以匹配，为"info.warn1"无法匹配
		args = new String[]{"info.*"};
		foo(args);
	}

	private static void foo(String[] args) {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			channel.exchangeDeclare(EXCHANGE_NAME, "topic");
			String queueName = channel.queueDeclare().getQueue();
			
			for(String severity : args) {
				channel.queueBind(queueName, EXCHANGE_NAME, severity);
				System.out.println("routingKey:"+severity);
			}
			Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
						byte[] body) throws IOException {
					String message = new String(body,"UTF-8");
					System.out.println(queueName+" Recv: "+message);
				}
			};
			channel.basicConsume(queueName, true, consumer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
