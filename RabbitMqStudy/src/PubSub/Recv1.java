package PubSub;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class Recv1 {
	private static final String QUEUE_NAME = "workqueue";
	private static final String EXCHANGE_NAME = "ex_log";
	public static void main(String[] args) {
		foo();
	}

	private static void foo() {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
			//创建一个随机的queue(非持久化、独占的、随着client session断开而自动删除的)
			String queueName = channel.queueDeclare().getQueue();
			channel.queueBind(queueName, EXCHANGE_NAME, "");
			DefaultConsumer consumer = new DefaultConsumer(channel){
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
						byte[] body) throws IOException {
					String message = new String(body,"UTF-8");
					System.out.println("Recv:"+message);
				}
			};
			//只能接收到绑定道queue之后producer生产的数据，如果生产者生产数据时没有queue绑定到exchange，那么消息自动丢弃
			channel.basicConsume(queueName, true, consumer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
