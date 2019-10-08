package com.topics;

import com.rabbitmq.client.*;
import com.util.MqConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Administrator
 */
public class TopicsRecv2 {
    private static String QUEUE_NAME = "topics2";
    private static String EXCHANGE_NAME = "topic";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = MqConnection.getConnection();
        final Channel channel = connection.createChannel();

        //声明对垒
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //绑定对垒
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"huangfu.#");

        channel.basicQos(1);

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body,"UTF-8"));
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };

        channel.basicConsume(QUEUE_NAME,false,consumer);
    }
}
