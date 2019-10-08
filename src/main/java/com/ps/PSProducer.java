package com.ps;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.util.MqConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author huangfu
 * 队列 消息生产者
 * 发布 订阅模式
 */
public class PSProducer {
    private static String EXCHANGE_NAME = "ps";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = MqConnection.getConnection();

        Channel channel = connection.createChannel();
        /**
         *  声明交换机
         *  fanout 不处理路由，分发给所有队列
         *  direct 处理路由 发送的时候需要发sing一个路由key
         */

        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");


        String msg = "醉卧沙场君莫笑";
        /**
         * 第二各参数
         *      匿名转发，路由key
         */
        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());
        channel.close();
        connection.close();
    }
}
