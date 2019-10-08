package com.topics;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.util.MqConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发布订阅模式
 * 主题模式
 * @author huangfu
 */
public class TopicsSend {
    private static String EXCHANGE_NAME = "topic";
    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = MqConnection.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME,"topic");

        String msg = "醉卧沙场君莫笑";
        channel.basicPublish(EXCHANGE_NAME,"huangfu.del",null,msg.getBytes());
        System.out.println("send:"+msg);
        channel.close();
        connection.close();
    }
}
