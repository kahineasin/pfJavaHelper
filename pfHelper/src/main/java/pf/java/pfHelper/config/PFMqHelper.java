package pf.java.pfHelper.config;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import pf.java.pfHelper.PFMq;
import pf.java.pfHelper.PFMqMessage;

import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.order.ConsumeOrderContext;
import com.aliyun.openservices.ons.api.order.MessageOrderListener;
import com.aliyun.openservices.ons.api.order.OrderAction;
import com.aliyun.openservices.ons.api.order.OrderConsumer;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.exception.MQClientException;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

/*
 * 为了兼容普通mq和alimq
 * 此类为单例,考虑调试方便,如果有多个 GroupId的情况,建新类PFMq来实现吧
 */
@Component //使用的类也要此特性
public class PFMqHelper {
	public  enum PFMqType{
		AliMq,RabbitMq
	}
	@FunctionalInterface
	public interface PFDeliverCallback {

	    /**
	     * Called when a <code><b>basic.deliver</b></code> is received for this consumer.
	     * @param consumerTag the <i>consumer tag</i> associated with the consumer
	     * @param message the delivered message
	     * @throws IOException if the consumer encounters an I/O error while processing the message
	     */
	    void handle(String consumerTag, PFMqMessage message) throws IOException;
	}

	private static PFMqType _mqType=PFMqType.RabbitMq;
    private static PFMqConfig _mqConfig;
    @Autowired
    public PFMqHelper(PFMqConfig mqConfig) {
    	PFMqHelper._mqConfig = mqConfig;
    	_mqType=PFMqType.valueOf(mqConfig.getMqType());
    }
	public static void BuildConsumer(PFDeliverCallback pfDeliverCallback) {
		switch(_mqType) {
			case RabbitMq:
				(new PFMq(_mqConfig)).BuildRabbitMqConsumer(pfDeliverCallback);
				break;
			case AliMq:
				(new PFMq(_mqConfig)).BuildAliMqConsumer(pfDeliverCallback);
				break;
			default:
				break;
		}
	}
	public static void BuildProducer(String message) {
		switch(_mqType) {
			case RabbitMq:
				(new PFMq(_mqConfig)).BuildMqProducer(message);
				break;
			case AliMq:
				(new PFMq(_mqConfig)).BuildAliMqProducer(message);
				break;
			default:
				break;
		}
	}
}
