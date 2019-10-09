package com.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.util.MqConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 路由  发布订阅模式
 * @author huangfu
 *
 * 路由转发
 * 根据  routingKey  进行转发
 *
 * 缺陷：路由表必须明确
 */
public class RoutingSend {
    private static String EXCHANGE_NAME = "routing";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = MqConnection.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");

        String msg = "醉卧沙场君莫笑";
        String routingKey = "error";
        channel.basicPublish(EXCHANGE_NAME,routingKey,null,msg.getBytes());
        System.out.println("send:"+msg);
        channel.close();
        connection.close();

    }
}
