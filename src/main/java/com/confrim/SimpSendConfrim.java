package com.confrim;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.util.MqConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author huangfu
 * 事务
 *
 * confrim
 *      普通：发一条
 *      批量的：发一批
 *      异步的：提供回调
 */
public class SimpSendConfrim {
    private static final String QUEUE_NAME = "tx_confrim";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = MqConnection.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String msg = "醉卧沙场君莫笑";
        //将管道设置为confirm模式
        channel.confirmSelect();
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

        if(! channel.waitForConfirms()){
            System.out.println("发送失败");
        }else{
            System.out.println("发送成功");
        }
        channel.close();
        connection.close();
    }
}
