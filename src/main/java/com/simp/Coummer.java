package com.simp;

import com.rabbitmq.client.*;
import com.util.MqConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者
 * @author huangfu
 */
public class Coummer {
    private static String QUEUE_NAME = "simp";
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection = MqConnection.getConnection();
        //创建频道
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
         * 事件触发
         * 一旦有消息进入到队列就会触发这个
         */
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body,"UTF-8"));
            }
        };
        /**
         * 监听队列
         * 1.要监听的队列名称
         * 2.是否自动应答 （自动确认消息） 确认后将从队列里删除该条消息
         * 3.回调函数
         */
        channel.basicConsume(QUEUE_NAME,true,defaultConsumer);

    }
}
