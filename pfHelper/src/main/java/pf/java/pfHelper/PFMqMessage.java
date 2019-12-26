package pf.java.pfHelper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.rabbitmq.client.Delivery;

public class PFMqMessage {
	private String message;

	public PFMqMessage(Delivery delivery) {
	   try {
		message=new String(delivery.getBody(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public PFMqMessage(com.aliyun.openservices.ons.api.Message aliMessage) {
			message=aliMessage.toString();
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
