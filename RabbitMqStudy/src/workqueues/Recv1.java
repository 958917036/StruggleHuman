package workqueues;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class Recv1 {
	private static final String QUEUE_NAME = "workqueue";
	public static void main(String[] args) {
		foo();
	}
	
	private static void foo() {
		try{
			int hashCode = Recv1.class.hashCode();
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			channel.queueDeclare(QUEUE_NAME, true, false, false, null);
			DefaultConsumer consumer = new DefaultConsumer(channel){
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
						byte[] body) throws IOException {
					String message = new String(body,"UTF-8");
					doWork(message);
				}
			};
			boolean autoack = true;
			channel.basicConsume(QUEUE_NAME, autoack, consumer);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private static void doWork(String task) {
		System.out.println("Received task:"+task);
		for(char ch : task.toCharArray()) {
			if(ch == '.') {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("task done:"+task);
	}
}
