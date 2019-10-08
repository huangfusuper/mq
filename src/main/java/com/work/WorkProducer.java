package com.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.util.MqConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author huangfu
 * 工作队列 消息生产者
 * 可以看到一个现象   消费者1和2 虽然处理时间不同 但是处理的数量是一样的
 * 这种状态属于轮训分发  公平分发必须关闭自动应答  ACK
 *
 *            --消费者
 *            =
 * 生产者-----=
 *            =
 *            --消费者
 *
 * 如果想要让处理速度快的处理多一点 需要使用basicQos(perfeth=1)
 * 每次只发送一条  只有你处理完成之后我才会给你发送下一条消息
 */
public class WorkProducer {
    private static String QUEUE_NAME = "work";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = MqConnection.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        for (int i = 0; i < 50; i++) {
            String msg = "醉卧沙场君莫笑"+i;
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            System.out.println(msg);
            Thread.sleep(i*10);
        }

        channel.close();
        connection.close();
    }
}
