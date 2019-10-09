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
         * 1.消息队列名
         * 2.是否队列持久化
         * 3.是否为排他队列
         * 4.是否队列自动删除
         * 5.配置项
         */

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        /**
         * 发送消息
         * 1.交换机名字
         * 2.队列名字
         * 3.配置
         * 4.消息体
         */
        channel.basicPublish("",QUEUE_NAME,null,"醉卧沙场君莫笑".getBytes());

        channel.close();
        connection.close();

    }
}
