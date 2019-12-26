mq的使用:
1.监听,在com.perfect99.receiveUnicomMq 
    加继承PFConsumerTask的Component类
2.发送,在com.perfect99.receiveUnicomMq.producer 
    加继承PFProdutTask的类XxProduct
  然后PFMqHelper.BuildProducer("message content",new XxProduct());调用
