package direct;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

//direct为exchange的一种模式，主要是按照binding key来决定将消息放到哪些队列里
//相对于广播模式fanout就是多了一层routingKey的匹配，向匹配的queue广播
//大致流程时 producer -> exchange -> queue -> consumer
//producer可以选择像哪个exchange中发送数据，同时可以指定routingKey，也可以指定queue
//exchange则根据当前的模式决定将消息放到其关联的哪些queue里，广播就是全部，direct就是根据routingKey匹配，routingKey是exchange和queue之间的识别符
//queue就是用来具体的存放数据的队列，其内的同一条消息只会发送给一个consumer（假设多个人绑定同一个queue），可以用来负载均衡
public class Send {
	private static final String EXCHANGE_NAME = "direct_logs";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		foo();
	}
	
	/**
	 *  Exchange的direct类型
	 *  发送端首先获取连接和channel，然后声明一个direct类型的exchange
	 */
	private static void foo() {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			//声明一个直连的exchange
			channel.exchangeDeclare(EXCHANGE_NAME, "direct");
			for (int i = 0; i < 3; i++) {
				//设定了routing key为info1，info2，info3
				String serverity = "info"+i;
				String message = "info test"+i;
				//在名为direct_logs的Exchange上绑定了三个routing key，并发送消息
				channel.basicPublish(EXCHANGE_NAME, serverity, null, message.getBytes());
				System.out.println("Send:"+message);
			}
			channel.close();
			connection.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
