package com.ais.brm.common.utils;

import com.ais.brm.common.domain.IKafkaProducerConfig;
import com.ais.brm.common.domain.IMessage;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * kafka生产者.
 * 使用方法：参考：datacollector模块的DataCollectService
 * Created by zhaocw on 2016/5/5.
 * @author zhaocw
 */
public final class KafkaProducer {
    private static Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    private Producer<String, String> producer;
    private IKafkaProducerConfig kafkaProducerConfig;
    private KafkaProducerRetrier kafkaProducerRetrier;

    public KafkaProducer(IKafkaProducerConfig kafkaProducerConfig) {
        this.kafkaProducerConfig = kafkaProducerConfig;
        kafkaProducerRetrier = new KafkaProducerRetrier(this);
    }

    //入口，其他模块调用此方法发送消息到kafka，此模块会保证消息被传递.

    /**
     * .
     * @param topic
     * @param message
     * @return
     */
    public boolean produce(String topic,IMessage message) {
        if(producer==null) {
            init();
        }
        try {
            producer.send(new ProducerRecord<>(topic, message.getContent()),
                    new Callback() {
                        @Override
                        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                            if(e!=null) {
                                logger.error("kafka message: topic="+topic+",message="+message+" sent failed",e);
                                kafkaProducerRetrier.addTodo(topic,message);
                                try {
                                    if(producer!=null) {
                                        producer.close();
                                        producer = null;
                                    }
                                }catch (Exception ex) {
                                    logger.error("",ex);
                                }
                            }else {
                                logger.debug("kafka message:topic="+topic+",message:"+message+" sent successfully.");
                                kafkaProducerRetrier.removeTodo(topic,message);
                            }
                        }
                    });
            producer.flush();
            return true;
        } catch (Exception e) {
            logger.error("",e);
            if(producer!=null) {
                producer.close();
                producer = null;//下次调用的时候会重新初始化.
            }
        }
        return false;
    }

    /**
     * .
     */
    private void init() {
        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaProducerConfig.getBootstrapServers());
        props.put("acks", "all");//确保消息被保存到kafka.
        props.put("retries", kafkaProducerConfig.getRetries());
        props.put("batch.size", kafkaProducerConfig.getBatchSize());
        props.put("linger.ms", 1);
        props.put("request.timeout.ms",10*60*1000);//在客户端producer缓存的最大时长
        props.put("metadata.fetch.timeout.ms",35*1000);
        props.put("buffer.memory", kafkaProducerConfig.getBufferMemory());
        
      //增加组超时时间 另外手动发消息需要修改server.property group.max.session.timeout.ms
   //     props.put("group.max.session.timeout.ms", kafkaProducerConfig.getGroupMaxSessionTimeoutMs());
    //    props.put("group.min.session.timeout.ms",0);

        
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new org.apache.kafka.clients.producer.KafkaProducer<String,String>(props);

    }

    /**
     * .
     */
    public void close() {
        if(producer!=null) {
            producer.close();
        }
    }


    public IKafkaProducerConfig getKafkaProducerConfig() {
        return kafkaProducerConfig;
    }
}
