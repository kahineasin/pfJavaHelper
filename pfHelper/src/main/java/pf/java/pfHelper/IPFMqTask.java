package pf.java.pfHelper;

import com.rabbitmq.client.Delivery;

public interface IPFMqTask {
	public void Listen(String consumerTag, Delivery message);
}
