package detail2;

import java.io.IOException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.ReturnListener;

public class CommonFunc {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
}
class MyReturnListener implements ReturnListener{
	
	
	/* 
	 * 当指定了发送模式为 mandatory且没发出去时(如：direct exchange没有绑定queue)，消息会被送回，需要设置 channel.addReturnListener(..);
	 * If a message is published with the "mandatory" flags set, but cannot be routed, the broker will return it to the sending client
	 */
	@Override
	public void handleReturn(int replyType, String replyTest, String exchange, String routingKey, BasicProperties properties, byte[] body)
			throws IOException {
		
	}
	
}
















