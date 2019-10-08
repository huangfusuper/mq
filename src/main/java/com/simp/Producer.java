package com.simp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.util.MqConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息生产者
 * @author huangfu
 */
public class Producer {
    private static String QUEUE_NAME = "simp";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection = MqConnection.getConnection();
        //从连接中获取一个通道
        Channel channel = connection.createChannel();
        /**
         *
         * 创建队列
         * 消息队列名
         * 是否队列持久化
         * 是否为排他队列
         * 是否队列自动删除
         * 配置项
         */

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        /**
         * 发送消息
         * 交换机名字
         * 队列名字
         * 配置
         * 消息体
         */
        channel.basicPublish("",QUEUE_NAME,null,"醉卧沙场君莫笑".getBytes());

        channel.close();
        connection.close();

    }
}
