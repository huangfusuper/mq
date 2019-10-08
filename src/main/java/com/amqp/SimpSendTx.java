package com.amqp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.util.MqConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author huangfu
 * 事务
 */
public class SimpSendTx {
    private static final String QUEUE_NAME = "tx_queue";
    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;

        try {
            connection = MqConnection.getConnection();

            channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME,false,false,false,null);

            String msg = "醉卧沙场君莫笑";

            channel.txSelect();

            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            int i = 5/0;
            channel.txCommit();
            System.out.println("提交成功");
        }catch (Exception e)

        {
            e.printStackTrace();
            try {
                channel.txRollback();
                System.out.println("回滚成功");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }finally {
            if(channel != null){
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


    }
}
