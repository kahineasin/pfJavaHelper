package pf.java.pfHelper;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendResult;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import pf.java.pfHelper.config.PFDataHelper;
import pf.java.pfHelper.config.PFMqConfig;
import pf.java.pfHelper.config.PFMqHelper.PFDeliverCallback;

public class PFMq {
    private  PFMqConfig _mqConfig;
	public PFMq(PFMqConfig mqConfig) {
		_mqConfig=mqConfig;
	}
	

	public  void BuildRabbitMqConsumer(PFDeliverCallback pfDeliverCallback) {

    	//rabbitmq
    	com.rabbitmq.client.ConnectionFactory factory = new com.rabbitmq.client.ConnectionFactory();
	    factory.setHost("localhost");
	    Connection connection;
		try {
			connection = factory.newConnection();
		    Channel channel = connection.createChannel();
		    String QUEUE_NAME=_mqConfig.getQueueName();
		    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
	
		    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
		    	pfDeliverCallback.handle(consumerTag, new PFMqMessage(delivery));
//		        String message = new String(delivery.getBody(), "UTF-8");
//		        System.out.println(" [x] Received '" + message + "'");
		    };
		    channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public  void BuildAliMqConsumer(PFDeliverCallback pfDeliverCallback) {
		//参考:D:\eclipse_workspace\IpaasTest\src\com\mq\simple\ConsumerTest.java
        Properties properties = new Properties();
        
        
//        // 您在控制台创建的 Consumer ID
//        //properties.put(PropertyKeyConst.ConsumerId, "CID_I_SCRM_PERFECT_TEST");
//        properties.put(PropertyKeyConst.GROUP_ID, "CID_I_SCRM_PERFECT_TEST");
//     // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
//        properties.put(PropertyKeyConst.AccessKey, "JVczMfdmcZ3MCVX4");
//     // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
//        properties.put(PropertyKeyConst.SecretKey, "gpgYF9JEaf4xlcKQtLKFiIhwBI5Vm8");
//     // 设置 TCP 接入域名（此处以公共云生产环境为例）
//        properties.put(PropertyKeyConst.NAMESRV_ADDR,
//          "172.100.2.212:9876;172.100.2.164:9876;172.100.15.35:9876");


        properties.put(PropertyKeyConst.GROUP_ID, _mqConfig.getGroupId());
     // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.AccessKey, _mqConfig.getAccessKey());
     // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, _mqConfig.getSecretKey());
     // 设置 TCP 接入域名（此处以公共云生产环境为例）
        properties.put(PropertyKeyConst.NAMESRV_ADDR,
        		_mqConfig.getNameSrvAddr());
        
        
        // 集群订阅方式 (默认)
        // properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.CLUSTERING);
        // 广播订阅方式
        // properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.BROADCASTING);
        
        Consumer consumer = ONSFactory.createConsumer(properties);
       /* consumer.subscribe("ljs_test122101", "test01||test02", new MessageListener() { //���Ķ��Tag
*/       
        
//        consumer.subscribe("YUNDT_CUBE_SINGLE_TOPIC_PERFECT_TEST", "YUNDT_CUBE_SINGLE_TAG_TIANGONG_TEST", new MessageListener() { //���Ķ��Tag	      
//        	public Action consume(Message message, ConsumeContext context) {
//                System.out.println("TIANGONG'S TEST FOR SOME PROBLEMS IS CONSUMING.START_TIME IS 2019-3-18 22:05" + message);
//                System.out.println("消息重试次数为：" + message.getReconsumeTimes());
//                throw new RuntimeException("Consumer Message exceotion");
//            }
//        });
        //订阅另外一个Topic
/*        consumer.subscribe("TopicTestMQ-Other", "*", new MessageListener() { //����ȫ��Tag
            public Action consume(Message message, ConsumeContext context) {
                System.out.println("Receive: " + message);
                return Action.CommitMessage;
            }
        });*/
		consumer.subscribe(_mqConfig.getTopic(), "*", new MessageListener() { //����ȫ��Tag
		    public Action consume(Message message, ConsumeContext context) {
					try {
						pfDeliverCallback.handle("", new PFMqMessage(message));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
		    	return Action.CommitMessage;
		    }
		});
        consumer.start();
	}

	public  void BuildMqProducer(String message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()
            		 ) {
        	try {
            channel.queueDeclare(_mqConfig.getQueueName(), false, false, false, null);
            channel.basicPublish("", _mqConfig.getQueueName(), null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        	}catch(Exception e) {
        		
        	}
        } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TimeoutException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	public  void BuildAliMqProducer(String message) {

		//参考:D:\eclipse_workspace\IpaasTest\src\com\mq\simple\ProducerTest.java
		Properties properties = new Properties();
//		
//        //您在控制台创建的 Producer ID
//        //properties.put(PropertyKeyConst.ProducerId, "PID_PERFECT_TEST");
//        //properties.put(PropertyKeyConst.GROUP_ID, "PID_PERFECT_TEST");
//        properties.put(PropertyKeyConst.GROUP_ID, "CID_I_SCRM_PERFECT_TEST");
//        // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
//        properties.put(PropertyKeyConst.AccessKey,"JVczMfdmcZ3MCVX4");
//        // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
//        properties.put(PropertyKeyConst.SecretKey, "gpgYF9JEaf4xlcKQtLKFiIhwBI5Vm8");
//        //设置发送超时时间，单位毫秒
//        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "3000");
//        // 设置 TCP 接入域名（此处以公共云生产环境为例）
//        /*properties.put(PropertyKeyConst.ONSAddr,
//        "http://mq.server.icloud.topone.local:8080/rocketmq/nsaddr4broker-internal");*/
//        properties.put(PropertyKeyConst.NAMESRV_ADDR,
//                "172.100.2.212:9876;172.100.2.164:9876;172.100.15.35:9876");
        

        //您在控制台创建的 Producer ID
        //properties.put(PropertyKeyConst.ProducerId, "PID_PERFECT_TEST");
        //properties.put(PropertyKeyConst.GROUP_ID, "PID_PERFECT_TEST");
        properties.put(PropertyKeyConst.GROUP_ID, _mqConfig.getGroupId());
        // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.AccessKey,_mqConfig.getAccessKey());
        // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, _mqConfig.getSecretKey());
        //设置发送超时时间，单位毫秒
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "3000");
        // 设置 TCP 接入域名（此处以公共云生产环境为例）
        /*properties.put(PropertyKeyConst.ONSAddr,
        "http://mq.server.icloud.topone.local:8080/rocketmq/nsaddr4broker-internal");*/
        properties.put(PropertyKeyConst.NAMESRV_ADDR,
                "172.100.2.212:9876;172.100.2.164:9876;172.100.15.35:9876");
        
        Producer producer = ONSFactory.createProducer(properties);
        
        // 在发送消息前，必须调用 start 方法来启动 Producer，只需调用一次即可
        producer.start();

        Message msg = new Message( //
            // Message 所属的 Topic
        		_mqConfig.getTopic(),
        		 "*",// Message Tag 可理解为 Gmail 中的标签，对消息进行再归类，方便 Consumer 指定过滤条件在 MQ 服务器过滤
            //"YUNDT_CUBE_SINGLE_TAG_TIANGONG_TEST",
            // Message Body 可以是任何二进制形式的数据， MQ 不做任何干预，
            // 需要 Producer 与 Consumer 协商好一致的序列化和反序列化方式
        		 message.getBytes()//"HELLO,THIS IS TIANGONG'S TEST FOR SOME PROBLEMS.START_TIME IS 2019-3-18 22:05".getBytes()
        		 );
        // 设置代表消息的业务关键属性，请尽可能全局唯一。
        // 以方便您在无法正常收到消息情况下，可通过阿里云服务器管理控制台查询消息并补发
        // 注意：不设置也不会影响消息正常收发
//        String msgKey="ORDERID_" + i;
        String msgKey=_mqConfig.getTopic()+ PFDataHelper.ObjectToDateString(Calendar.getInstance(), "yyyyMMddHHmmss");
        msg.setKey(msgKey);
        try {
            SendResult sendResult = producer.send(msg);
            // 同步发送消息，只要不抛异常就是成功
            if (sendResult != null) {
                System.out.println(new Date() + " ---------HELLO,THIS IS TIANGONG'S TEST FOR SOME PROBLEMS.START_TIME IS 2019-3-18 22:05----------------:" + msg.getTopic()+" Key:" + msgKey + " msgId is: " + sendResult.getMessageId());
            }
        }
        catch (Exception e) {
            // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理
            System.out.println(new Date() + " TIANGONG TEST -Send mq message failed. Topic is:" + msg.getTopic());
            e.printStackTrace();
        }
        
//        //循环发送消息
//        for (int i = 0; i < 100; i++){
//            Message msg = new Message( //
//                // Message 所属的 Topic
//                "YUNDT_CUBE_SINGLE_TOPIC_PERFECT_TEST",
//                // Message Tag 可理解为 Gmail 中的标签，对消息进行再归类，方便 Consumer 指定过滤条件在 MQ 服务器过滤
//                "YUNDT_CUBE_SINGLE_TAG_TIANGONG_TEST",
//                // Message Body 可以是任何二进制形式的数据， MQ 不做任何干预，
//                // 需要 Producer 与 Consumer 协商好一致的序列化和反序列化方式
//                "HELLO,THIS IS TIANGONG'S TEST FOR SOME PROBLEMS.START_TIME IS 2019-3-18 22:05".getBytes());
//            // 设置代表消息的业务关键属性，请尽可能全局唯一。
//            // 以方便您在无法正常收到消息情况下，可通过阿里云服务器管理控制台查询消息并补发
//            // 注意：不设置也不会影响消息正常收发
//            String msgKey="ORDERID_" + i;
//            msg.setKey(msgKey);
//            try {
//                SendResult sendResult = producer.send(msg);
//                // 同步发送消息，只要不抛异常就是成功
//                if (sendResult != null) {
//                    System.out.println(new Date() + " ---------HELLO,THIS IS TIANGONG'S TEST FOR SOME PROBLEMS.START_TIME IS 2019-3-18 22:05----------------:" + msg.getTopic()+" Key:" + msgKey + " msgId is: " + sendResult.getMessageId());
//                }
//            }
//            catch (Exception e) {
//                // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理
//                System.out.println(new Date() + " TIANGONG TEST -Send mq message failed. Topic is:" + msg.getTopic());
//                e.printStackTrace();
//            }
//        }
        // 在应用退出前，销毁 Producer 对象
        // 注意：如果不销毁也没有问题
        producer.shutdown();
	}
}
