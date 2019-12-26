package pf.java.pfHelper.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import pf.java.pfHelper.config.PFMqHelper.PFMqType;

@Component
public class PFMqConfig  implements Cloneable{

    @PostConstruct
    public void beforeInit() {
    }
  @Value("${pf.mq.mqType}")
  private  String mqType;

  //rabbitMq
  @Value("${pf.mq.queueName}")
  private  String queueName;
  
	//aliMq
	  @Value("${pf.mq.groupId}")
	  private  String groupId;
	  @Value("${pf.mq.nameSrvAddr}")
	  private  String nameSrvAddr;
	  @Value("${pf.mq.accessKey}")
	  private  String accessKey;
	  @Value("${pf.mq.secretKey}")
	  private  String secretKey;
	  @Value("${pf.mq.topic}")
	  private  String topic;

//	  public String getMqType() {
//		return mqType;
//	}
//	public void setMqType(String mqType) {
//		this.mqType = mqType;
//	}
	  public PFMqType getMqType() {
		return PFMqType.valueOf(mqType);
	}
	public void setMqType(PFMqType mqType) {
		this.mqType = mqType.toString();
	}

	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	  public String getGroupId() {
		return groupId;
		}
		public void setGroupId(String groupId) {
			this.groupId = groupId;
		}
		public String getNameSrvAddr() {
			return nameSrvAddr;
		}
		public void setNameSrvAddr(String nameSrvAddr) {
			this.nameSrvAddr = nameSrvAddr;
		}
		public String getAccessKey() {
			return accessKey;
		}
		public void setAccessKey(String accessKey) {
			this.accessKey = accessKey;
		}
		public String getSecretKey() {
			return secretKey;
		}
		public void setSecretKey(String secretKey) {
			this.secretKey = secretKey;
		}
		public String getTopic() {
			return topic;
		}
		public void setTopic(String topic) {
			this.topic = topic;
		}
		   @Override  
		    public Object clone() {  
			   PFMqConfig stu = null;  
		        try{  
		            stu = (PFMqConfig)super.clone();  
		        }catch(CloneNotSupportedException e) {  
		            e.printStackTrace();  
		        }  
		        return stu;  
		    }  

}
