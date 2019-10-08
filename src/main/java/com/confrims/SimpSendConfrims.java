package com.confrims;

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
 *      *批量的：发一批
 *      异步的：提供回调
 */
public class SimpSendConfrims {
    private static final String QUEUE_NAME = "tx_confrims";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = MqConnection.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String msg = "醉卧沙场君莫笑";
        //将管道设置为confirm模式
        channel.confirmSelect();
        for (int i = 0; i < 10; i++) {
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            System.out.println(msg);
            Thread.sleep(1000);
        }


        if(! channel.waitForConfirms()){
            System.out.println("发送失败");
        }else{
            System.out.println("发送成功");
        }
        channel.close();
        connection.close();
    }
}
