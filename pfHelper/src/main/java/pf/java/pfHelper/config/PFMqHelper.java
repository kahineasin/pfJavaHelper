package pf.java.pfHelper.config;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import pf.java.pfHelper.PFMq;
import pf.java.pfHelper.PFMqMessage;
/*
 * 为了兼容普通mq和alimq
 * 此类为单例,考虑调试方便,如果有多个 GroupId的情况,建新类PFMq来实现吧
 */
@Component //使用的类也要此特性
public class PFMqHelper {
	public  enum PFMqType{
		AliMq,RabbitMq
	}
	//@FunctionalInterface
	public interface PFConsumerTask {
	    void handle(String consumerTag, PFMqMessage message) ;
	    PFMqConfig GetMqConfig(PFMqConfig mqConfig);
	    
	}
	public interface PFProdutTask {
	    PFMqConfig GetMqConfig(PFMqConfig mqConfig);
	    
	}

	private static PFMqType _mqType=PFMqType.RabbitMq;
    private static PFMqConfig _mqConfig;
    private static ApplicationContext _applicationContext;
    @Autowired
    public PFMqHelper(PFMqConfig mqConfig,ApplicationContext applicationContext) {
    	PFMqHelper._mqConfig = mqConfig;
    	PFMqHelper._applicationContext=applicationContext;
    	//_mqType=PFMqType.valueOf(mqConfig.getMqType());
    	_mqType=mqConfig.getMqType();
    }
	public static void BuildConsumer(PFConsumerTask task) {
		 PFMqConfig mqConfig=task.GetMqConfig(PFMqConfig.class.cast(_mqConfig.clone()));
		 PFMqType mqType=mqConfig.getMqType();
		switch(mqType) {
			case RabbitMq:
				(new PFMq(mqConfig)).BuildRabbitMqConsumer(task);
				break;
			case AliMq:
				(new PFMq(mqConfig)).BuildAliMqConsumer(task);
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
	public static void BuildProducer(String message,PFProdutTask task) {
		 PFMqConfig mqConfig=task.GetMqConfig(PFMqConfig.class.cast(_mqConfig.clone()));
		 PFMqType mqType=mqConfig.getMqType();
		switch(mqType) {
			case RabbitMq:
				(new PFMq(mqConfig)).BuildMqProducer(message);
				break;
			case AliMq:
				(new PFMq(mqConfig)).BuildAliMqProducer(message);
				break;
			default:
				break;
		}
	}
 
	public static void ListenMq() {
	   
		   Map<String,PFConsumerTask> res = _applicationContext.getBeansOfType(PFConsumerTask.class);
		   Iterator<Entry<String, PFConsumerTask>> iter = res.entrySet().iterator();
		   while(iter.hasNext()){
			   Entry<String, PFConsumerTask> key=iter.next();
			   PFConsumerTask tmpObj;
				try {
					tmpObj = PFConsumerTask.class.cast(key.getValue().getClass().newInstance());
				    PFMqHelper.BuildConsumer(tmpObj);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		   }
	}
	
}
