package rpc;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.RecoverableChannel;

public class RpcClient {
	private static final String RPC_QUEUE_NAME = "rpc_queue4";
	private static final String EXCHANGE_NAME = "rpc_exchange4";
	private static final String ROUTING_KEY = "rpc_routingkey4";
	private Connection connection;
    private Channel channel;
    //生命周期随client的一个随机queue,用于接收应答数据
    private String replyQueue;
    String correlationId;
    final BlockingQueue<String> response = new ArrayBlockingQueue<String>(1);
    
    /** 获取connection，channel和replyQueue
     * @throws IOException
     * @throws TimeoutException
     */
    public RpcClient() throws IOException, TimeoutException {
    	ConnectionFactory factory = new ConnectionFactory();
    	factory.setHost("localhost");
    	
    	connection = factory.newConnection();
    	channel = connection.createChannel();
    	replyQueue = channel.queueDeclare().getQueue();
    	System.out.println("replyQueue:"+replyQueue);
    }
    
    public static void main(String[] args) throws IOException, TimeoutException {
    	start();
	}

    private static void start() {
    	RpcClient fibonacciRpc = null;
    	try {
    	 	fibonacciRpc = new RpcClient();
    		System.out.println(" [send] Requesting fib(3)");
    		String response = fibonacciRpc.call("3");
    		System.out.println(" [recv] Got '" + response + "'");
    	} catch(Exception e) {
    		e.printStackTrace();
    	} finally {
    		if(fibonacciRpc != null) {
    			try {
					fibonacciRpc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    	}
    }

    public String call(String message) throws IOException, InterruptedException {
        send(message);
        String answer = recv(correlationId);
        return answer;
    }
    
    public void send(String message) throws IOException {
    	//获取一个随机的uuid
        correlationId = UUID.randomUUID().toString();
        //将刚才获取的随机queue和uuid绑定到properties上，Server根据这些信息将应答发回这个queue中
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(correlationId)
                .replyTo(replyQueue)
                .build();
        //声明exchange和queue，并且把它们绑定起来，因为如果client先运行的话，必须queue存在，否则会被丢弃
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
        channel.queueBind(RPC_QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        //发送消息到exchange上（默认name为空""），绑定routingKey为rpc_routingkey,携带properties属性和请求数据
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, props, message.getBytes("UTF-8"));
    }
    
    public String recv(String correlationId) throws IOException, InterruptedException {
    	//初始化consumer用以回掉处理mqClient上报的消息
        Consumer consumer = new MyConsumer(channel, correlationId);
        //接收replyQueue中的消息，这里设置autoAck为true，设置消息处理类
        //channel.basicConsume(replyQueue, true, consumer);
        channel.basicConsume(replyQueue, true, "client_consumerTag", consumer);
        return response.take();
    }
    

    public void close() throws IOException {
        connection.close();
    }
    
    /**
     * 定义自己的消息处理类
     * @author gc
     *
     */
    class MyConsumer extends DefaultConsumer {
    	Channel channel;
    	String correlationId;
        public MyConsumer(Channel channel) {
			super(channel);
		}
        public MyConsumer(Channel channel,String uuid) {
        	super(channel);
        	this.channel = channel;
        	this.correlationId = uuid;
        }

		/* 
		 * 处理上报消息的回掉函数，
		 * @param consumerTag
		 * @param envelope
		 * @param properties
		 * @param body 应答的消息体
		 */
		@Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
			RpcServer.printFlags("client_recv", consumerTag, envelope, properties);
			if (properties.getCorrelationId().equals(correlationId)) {
                response.offer(new String(body, "UTF-8"));
            }
        }
    }
}
