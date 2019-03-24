package detail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

	private final String QUEUE_NAME = "detail_queue";
	private final String EXCHANGE_NAME = "detail_change";
	private final String ROUTING_KEY = "detail_routingKey";
	
	public static void main(String[] args) {
		Send send = new Send();
		send.foo();
	}

	Connection connection = null;
	//channel非线程安全,每个channel有它自己调度线程，一般一个consumer使用一个channel
	Channel channel = null;
	private void foo() {
		connection = getConnection();
		try {
			channel = connection.createChannel();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//声明一个我们将要发送数据进去的queue，然后向其发送数据。queue的声明是幂等的-只有不存在的话才会实际去创建
		//数据内容是byte数组
		prepare(true);
		try {
			publish("hello".getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
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
	private void publish(byte[] msg) {
		try {
			//定义消息的元数据，控制消息的属性，生命周期
			BasicProperties properties = new AMQP.BasicProperties.Builder()
					.deliveryMode(2).priority(1).userId("guest").expiration("100000").build();
			channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, properties, msg);
			System.out.println("Send:"+new String(msg));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * connection是一个tcp长连接
	 * 下面的参数都是rabbitMq的默认参数，可以不显示设置
	 */
	public Connection getConnection() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername("guest");
		factory.setPassword("guest");
		factory.setVirtualHost("/");
		factory.setHost("localhost");
		factory.setPort(5672);
		Connection connection = null;
		try {
			//factory.setUri("amqp://guest:guest@localhost:5672/");
			connection = factory.newConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	/**
	 * 显示的删除队列和exchange
	 */
	private void clean() {
		try {
			channel.queuePurge(QUEUE_NAME);		//删除队列中的数据
			channel.queueDelete(QUEUE_NAME);	//强制删除队列
			boolean ifUnused = true;
			boolean ifEmpty = true;
			//表示只有队列为空且当前无人使用时（没有消费者）才删除
			channel.queueDelete(QUEUE_NAME, ifUnused, ifEmpty);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			channel.exchangeDelete(EXCHANGE_NAME);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * connection是一个长连接，建议长久使用
	 * channel生命周期一般是每次操作完成后关闭
	 * 理论上关闭connection之后，channel会自动关闭，但是最好还是显示的关闭
	 */
	private void close() {
		try {
			channel.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
