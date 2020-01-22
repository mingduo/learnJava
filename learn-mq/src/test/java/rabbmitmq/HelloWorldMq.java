package com.ais.brm.study.brmTest.rabbmitmq;

import com.rabbitmq.client.*;
import lombok.Cleanup;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018/10/15</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class HelloWorldMq {
    private final static String HOST = "172.26.3.244";

    private final static String QUEUE_NAME = "hello";

    @Test
    public void produce() {
        ConnectionFactory factory = getConnectionFactory(HOST);
        try {
            @Cleanup Connection connection = factory.newConnection();
            @Cleanup Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void consumer() {
        ConnectionFactory factory = getConnectionFactory(HOST);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel();
        ) {


            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");


            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(" [x] Received '" + message + "'");
                }
            };
            channel.basicConsume(QUEUE_NAME, true, consumer);

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }


    }

    private ConnectionFactory getConnectionFactory(String localhost) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(localhost);
        factory.setUsername("root");
        factory.setPassword("root");
        return factory;
    }
}
