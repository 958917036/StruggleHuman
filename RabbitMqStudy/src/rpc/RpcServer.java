package rpc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class RpcServer {
	private static final String RPC_QUEUE_NAME = "rpc_queue4";
	private static final String EXCHANGE_NAME = "rpc_exchange4";
	private static final String ROUTING_KEY = "rpc_routingkey4";
	private static Channel channel; 
	private static Connection connection;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		start();
	}
	/**
	 * 初始化连接，通道，声明exchange，queue,绑定queue，设置最多同时处理1条待应答的请求
	 * @throws IOException
	 * @throws TimeoutException
	 */
	private static void init() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		connection = factory.newConnection();
		channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
		channel.queueBind(RPC_QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
		channel.basicQos(1);
	}
	/**
	 * @param consumer 接收消息的回调处理函数
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private static void processRequest(Consumer consumer) throws IOException, InterruptedException {
		channel.basicConsume(RPC_QUEUE_NAME, false, consumer);
		//设置成下面这样，就不能处理多条消息?
		//channel.basicConsume(RPC_QUEUE_NAME, true, "server_consumerTag", consumer);
		while(true) {
			synchronized(consumer) {
				consumer.wait();
			}
		}
	}
	/**
	 *  功能入口
	 */
	private static void start() {
		try {
			init();
			Consumer consumer = new MyConsumer(channel);
			processRequest(consumer);
		} catch(Exception e) {
			
		} finally {
			if (channel != null) {
				try {
					channel.close();
				} catch (IOException | TimeoutException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private static int fib(int i) {
		return i * ++i;
	}

	/**
	 * 接收客户端的请求，调用函数处理，然后返回应答
	 *
	 */
	static class MyConsumer extends DefaultConsumer{
		Channel channel = null;
		public MyConsumer(Channel channel) {
			super(channel);
			this.channel = channel;
		}

		@Override
		public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
				byte[] body) throws IOException {
			printFlags("server_recv", consumerTag, envelope, properties);
			AMQP.BasicProperties replyProps = new AMQP.BasicProperties().builder().
					correlationId(properties.getCorrelationId()).build();
			String response = "";
			String message = new String(body,"UTF-8");
			System.out.println("Server recv:"+message);
			int n = Integer.parseInt(message);
			response += fib(n);
			try {
				sendResponse(properties,replyProps,envelope,response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * 向client端发送应答
		 */
		private void sendResponse(BasicProperties properties,AMQP.BasicProperties replyProps,Envelope envelope,String response) throws Exception {
			//将消息发送到默认的exchange中，对应routing key为client端绑定的queue（待确认理解：对于默认exchange，routingkey默认和queue同名）
			channel.basicPublish("", properties.getReplyTo(), replyProps, response.getBytes("UTF-8"));
			channel.basicAck(envelope.getDeliveryTag(), false);
			System.out.println("Server send:"+response);
			synchronized(this) {
				this.notify();
			}
		}
	}
	
	/**
	 * @param prefix 打印日志的前缀
	 * @param consumerTag 定义basicConsumer时指定的tag，不指定的话系统会自动生成一个
	 * @param envelope 消息的打包信息：
	 * 				deliveryTag消息发送的编号, 表示这条消息是mq发送的第几条消息，1表示第一条
	 * 				redeliver 重传标志，表示是否是重传消息（上一个消费者处理失败）
	 * 				exchange 消息发送到的exchange的名字
	 * 				routingKey 路由key
	 * @param properties 关于消息的一些属性信息，这些属性需要发送者显示设置的，如此例中client端设置了下面两个属性
	 * 				correlation-id 表示这条消息关联的id
	 * 				reply-to 表示这条消息需要发送到哪个routingkey中
	 */
	public static void printFlags(String prefix, String consumerTag, Envelope envelope, AMQP.BasicProperties properties) {
    	System.out.println(prefix+"_consumerTag:"+consumerTag);
        System.out.println(prefix+"_envelope:"+envelope);
        System.out.println(prefix+"_properties:"+properties);
    }
}

