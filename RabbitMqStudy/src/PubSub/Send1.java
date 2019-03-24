package PubSub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send1 {
	private static final String QUEUE_NAME = "workqueue";
	private static final String EXCHANGE_NAME = "ex_log";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		foo();
	}

	/**
	 *  测试Exchange的fanout类型：即广播模式，不关心queue和exchange的binding key。
	 *  本例测试场景是发送端发送日志消息到mq，消费者打印实时日志
	 *  所以这里没有为exchange绑定queue，这意味着如果生产者先发送数据，那么消息会丢失，消费者只能收到其起来之后生产者发送的消息
	 *  因为只有queue才能存数据，本例中只有消费者声明了queue（绑定的是随机queue，生命周期同消费者）
	 *  这样做的好处是，一个消费者一个queue，消费者挂了queue也会自动删除
	 */
	private static void foo() {
		try{
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			
			channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
			String message = "this is a log";
			//下面第一个参数时exchange的名字，之前为空""，表示为匿名，只要对端也为空""即可
			channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
			System.out.println("Send:"+message);
			channel.close();
			connection.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
