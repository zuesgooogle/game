package com.s4game.core.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 
 * @Author zeusgooogle@gmail.com
 * @sine 2015年5月4日 上午11:23:25
 * 
 */
public class ServerListener {

    private Logger LOG = LoggerFactory.getLogger(getClass());

    private int port;

    private int bossThreadSize;

    private int workerThreadSize;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    private ChannelInitializer<SocketChannel> initializer;

    private boolean success = false;

    public void start() {
        bossGroup = new NioEventLoopGroup(getBossThreadSize());
        workerGroup = new NioEventLoopGroup(getWorkerThreadSize());

        ServerBootstrap bootstarp = new ServerBootstrap();
        bootstarp.group(bossGroup, workerGroup);
        bootstarp.channel(NioServerSocketChannel.class);
        bootstarp.childHandler(initializer);

        try {
            bootstarp.bind(port).sync();
            
            this.success = true;
            
            LOG.info("net server started successfully. bind port: {}", port);
        } catch (InterruptedException e) {
            
            LOG.error("net server boot failed bind port: {}", port, e);
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        if (success) {
            this.bossGroup.shutdownGracefully();
            this.workerGroup.shutdownGracefully();
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getBossThreadSize() {
        return bossThreadSize;
    }

    public void setBossThreadSize(int bossThreadSize) {
        if (bossThreadSize <= 0) {
            throw new RuntimeException("bossThreadSize must be more then 0");
        }
        
        this.bossThreadSize = bossThreadSize;
    }

    public int getWorkerThreadSize() {
        return workerThreadSize;
    }

    public void setWorkerThreadSize(int workerThreadSize) {
        if (workerThreadSize <= 0) {
            throw new RuntimeException("workerThreadSize must be more then 0");
        }
        
        this.workerThreadSize = workerThreadSize;
    }

    public void setInitializer(ChannelInitializer<SocketChannel> initializer) {
        this.initializer = initializer;
    }
}
