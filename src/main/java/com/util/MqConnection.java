package com.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 获取MQ的连接
 * @author Administrator
 */
public class MqConnection {
    /**
     * 获取连接的方法
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    public static Connection getConnection() throws IOException, TimeoutException {
        //连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置服务地址
        connectionFactory.setHost("127.0.0.1");
        //设置服务端口
        connectionFactory.setPort(5672);
        //设置登录用户
        connectionFactory.setUsername("user");
        connectionFactory.setPassword("user");
        //设置VirtualHost  （相当于数据库  ）
        connectionFactory.setVirtualHost("/user");

        return connectionFactory.newConnection();
    }
}
