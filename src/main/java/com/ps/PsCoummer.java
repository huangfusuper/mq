package com.ps;

import com.rabbitmq.client.*;
import com.util.MqConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Administrator
 */
public class PsCoummer {
    private static final String QUEUE_NAME = "ps";
    private static final String EXCHANGE_NAME = "ps";
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection = MqConnection.getConnection();
        //创建频道
        final Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        /**
         * 告诉消费者每次只发一个给消费者
         * 必须消费者发送确认消息之后我才会发送下一条
         */
        channel.basicQos(1);
        //绑定给交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");

        //定义一个消费者
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body,"UTF-8"));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[1] done");
                    //发送回执
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        /**
         * 第二个参数
         *      true:自动确认
         *          一旦mq将消息分发给消费者  就会从内存中删除，会出现消息丢失
         *      false:手动确认（默认）
         *          如果消费者挂掉，我将此消息发送给其他消费者
         *          支持消息应答，当消费者处理完成后发送给生产者回执，删除消息
         *
         *
         *      当消息队列宕了  内存里的数据依旧会丢失，此时需要将数据持久化
         */
        channel.basicConsume(QUEUE_NAME,false,consumer);
    }
}
