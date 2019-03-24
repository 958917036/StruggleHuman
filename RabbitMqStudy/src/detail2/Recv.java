package detail2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class Recv {

	private final String QUEUE_NAME = "detail_queue";
	private final String EXCHANGE_NAME = "detail_change";
	private final String ROUTING_KEY = "detail_routingKey";
	
	public static void main(String[] args) {
		new Recv().foo();
	}
	Connection connection = null;
	//channel非线程安全
	Channel channel = null;
	private void foo() {
		try {
			//这里复用了一下代码
			connection = new Send().getConnection();
			channel = connection.createChannel();
			prepare(true);
			boolean autoAck = false;
			String consumerTag = "consumer_test";
			channel.basicConsume(QUEUE_NAME, autoAck,new MyConsumer(channel));
			System.out.println("wait message");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	private void prepare(boolean isNamingQueue) {
		try {
			String exchangeType = "direct";
			//a durable, non-autodelete exchange of "direct" type
			channel.exchangeDeclare(EXCHANGE_NAME, exchangeType,true);
			if(isNamingQueue) {
				boolean durable = true;
				boolean exclusive = false;
				boolean autoDelete = false;
				//a durable, non-exclusive, non-autodelete queue with a well-known name
				channel.queueDeclare(QUEUE_NAME, durable, exclusive, autoDelete, null);
				channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
			} else {
				//建立 非持久化、独占、自动删除的队列（non-durable, exclusive, autodelete queue）
				//只有一个客户端可以使用这个队列(exclusive),有自动删除功能，生命周期随客户端连接
				String queueName = channel.queueDeclare().getQueue();
				channel.queueBind(queueName, EXCHANGE_NAME, ROUTING_KEY);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	class MyConsumer extends DefaultConsumer {

		public MyConsumer(Channel channel) {
			super(channel);
		}
		/* 
		 * Distinct Consumer instances must have distinct consumer tags
		 */
		@Override
		public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
				throws IOException {
			String routingKey = envelope.getRoutingKey();
			String exchangeName = envelope.getExchange();
			String expoiration = properties.getExpiration();
			long deliveryTag = envelope.getDeliveryTag();
			System.out.println("routingKey:"+routingKey+" ,exchangeName:"+exchangeName+" ,expoiration:"+expoiration
					+" ,deliveryTag:"+deliveryTag);
			String message = new String(body,"UTF-8");
			System.out.println("Received:"+message);
			boolean multiple  = false;
			channel.basicAck(deliveryTag, multiple);
		}
	}
}
