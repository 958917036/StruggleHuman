package workqueues;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class Recv2 {
	private static final String QUEUE_NAME = "workqueue";
	public static void main(String[] args) {
		foo();
	}
	
	private static void foo() {
		try{
			int hashCode = Recv2.class.hashCode();
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			channel.queueDeclare(QUEUE_NAME, true, false, false, null);
			//同时处理的待ack的请求的数量
			int prefetchCount = 3;
			channel.basicQos(prefetchCount);
			//关闭autoAck，表示请求处理完需要Consumer显示的发送应答，否则MQ认为消息未得到处理，后续会发给其他人处理
			boolean autoAck = false;
			DefaultConsumer consumer = new DefaultConsumer(channel){
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
						byte[] body) throws IOException {
					String message = new String(body,"UTF-8");
					doWork(message);
					//设定每处理完一条消息都要发送ack，只有发送完ack的，rabbitMq才会认为得到正确处理，否则会发送给其它人
					channel.basicAck(envelope.getDeliveryTag(), autoAck);
				}
			};
			//设定需要显示发送ack
			channel.basicConsume(QUEUE_NAME, autoAck, consumer);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private static void doWork(String task) {
		System.out.println("Received task:"+task);
		for(char ch : task.toCharArray()) {
			if(ch == '.') {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("task done:"+task);
	}
}
