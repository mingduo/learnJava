package com.ais.brm.common.utils;

import com.ais.brm.common.Constants;
import com.ais.brm.common.domain.IKafkaProducerConfig;
import com.ais.brm.common.domain.IMessage;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 负责进行retry.
 * Created by zhaocw on 2016/6/7.
 * @author zhaocw
 */
public class KafkaProducerRetrier {
    private static final long RETRY_WAIT_TIME_OUT = 2* 60 * 1000;
    private static Logger logger = LoggerFactory.getLogger(KafkaProducerRetrier.class);
    private KafkaProducer kafkaProducer;
    private RedisUtils redisUtils;
    private static Gson gson = new Gson();
    private volatile static Thread retryThread;
    private static Object lock = new Object();
    private IKafkaProducerConfig producerConfig;
    private int lockObject =0 ;

    public KafkaProducerRetrier(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
        this.producerConfig = kafkaProducer.getKafkaProducerConfig();
        redisUtils = new RedisUtils(this.producerConfig);
    }

    /**
     * 将todo任务加入redis.
     * @param topic
     * @param message
     */
    public void addTodo(String topic, IMessage message) {
        try {
            TodoTask task = new TodoTask(topic, message.getContent());
            redisUtils.rpush(Constants.REDIS_KEY_KAFKA_RETRY_LIST
                    + kafkaProducer.getKafkaProducerConfig().getName(), task.getKey());
            redisUtils.setStringData(Constants.REDIS_KEY_KAFKA_RETRY_MAP
                    + kafkaProducer.getKafkaProducerConfig().getName(), task.getKey(), gson.toJson(task));
            initRetryThread();
        }catch (Exception ex) {
            logger.error("",ex);
        }
    }

    /**
     * 查询并删除所有的todos.
     * @return
     */
    private List<TodoTask> popTodos() {
        try {
            List<String> strList = redisUtils.lrange(Constants.REDIS_KEY_KAFKA_RETRY_LIST
                    + kafkaProducer.getKafkaProducerConfig().getName(), 0, -1);
            redisUtils.ltrim(Constants.REDIS_KEY_KAFKA_RETRY_LIST
                    + kafkaProducer.getKafkaProducerConfig().getName(), 1, 0);
            if (strList != null && strList.size() > 0) {
                List<TodoTask> result = new ArrayList<>();
                for (String key : strList) {
                    String json = redisUtils.getStringData(Constants.REDIS_KEY_KAFKA_RETRY_MAP
                            + kafkaProducer.getKafkaProducerConfig().getName(), key);
                    if (StringUtils.isNotEmpty(json)) {
                        try {
                            TodoTask todoTask = gson.fromJson(json, TodoTask.class);
                            if (todoTask != null) {
                                result.add(todoTask);
                            }
                        } catch (Exception ex) {
                            logger.error("", ex);
                        }
                    }
                }
                return result;
            }
        }catch (Exception ex) {
            logger.error("",ex);
        }
        return null;
    }

    private void initRetryThread() {
        if(retryThread==null) {
            retryThread = new RetryThread();
        }
        if(!retryThread.isAlive()) {
            retryThread.start();
        }
    }

    /**
     * 将todo任务从redis删除.
     * @param topic
     * @param message
     */
    public void removeTodo(String topic, IMessage message) {
        try {
            TodoTask task = new TodoTask(topic, message.getContent());
            redisUtils.lrem(Constants.REDIS_KEY_KAFKA_RETRY_LIST
                    + kafkaProducer.getKafkaProducerConfig().getName(), task.getKey());
            redisUtils.delStringData(Constants.REDIS_KEY_KAFKA_RETRY_MAP
                    + kafkaProducer.getKafkaProducerConfig().getName(), task.getKey());
        }catch (Exception ex) {
            logger.error("",ex);
        }

        synchronized (lock) {//有成功案例了，可以开始重试了.
            lockObject = 1;
            lock.notify();
        }

    }

    /**
     * .
     * @return
     */
    public long countTodo() {
        try {
            return redisUtils.llen(Constants.REDIS_KEY_KAFKA_RETRY_LIST
                    + kafkaProducer.getKafkaProducerConfig().getName());
        }catch (Exception ex) {
            logger.error("",ex);
        }
        return 0;
    }

    private class TodoTask {
        String topic;
        String content;

        public TodoTask(String topic, String content) {
            this.topic = topic;
            this.content = content;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getKey() {
            try {
                return MD5Encoder.getInstance().md5Encode(topic+content);
            } catch (Exception e) {
                return String.valueOf((topic+content).hashCode());
            }
        }
    }

    //负责真正的retry.
    private class RetryThread extends Thread {
        @Override
        public void run() {
            while(true) {
                synchronized (lock) {
                    try {
                        logger.info("{} producer retry thread start waiting ...",producerConfig.getName());
                        lock.wait(RETRY_WAIT_TIME_OUT);
                        logger.info("{} producer retry thread wake up.",producerConfig.getName());
                        long count = countTodo();
                        logger.info("{} producer retry thread got {} todo tasks",producerConfig.getName(),count);
                        if(count>0) {
                            List<TodoTask> todoList = popTodos();
                            realRetry(todoList);
                            logger.info("{} producer retry thread retry done.",producerConfig.getName());
                        }else {
                            logger.warn("{} producer retry thread got no task to retry.",producerConfig.getName());
                        }
                        if(lockObject==0) {}
                    } catch (Exception e) {
                        logger.error("",e);
                    }
                }
            }
        }

        private void realRetry(List<TodoTask> todoList) {
            for(TodoTask todoTask:todoList) {
                kafkaProducer.produce(todoTask.getTopic(), new IMessage() {
                    @Override
                    public String getContent() {
                        return todoTask.getContent();
                    }
                });
            }
        }
    }


}
