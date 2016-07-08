package com.s4game.core.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * 
 * @Author zeusgooogle@gmail.com
 * @sine 2015年5月4日 上午11:23:25
 * 
 */
public class ClientListener {

    private Logger LOG = LoggerFactory.getLogger(getClass());

	private String host;

	private int port;

	private ChannelInitializer<SocketChannel> initializer;

	private boolean success = false;

	private Channel channel;
	
	public void start() {
		NioEventLoopGroup group = new NioEventLoopGroup();

		Bootstrap bootstarp = new Bootstrap();
		bootstarp.group(group)
				 .channel(NioSocketChannel.class)
				 .handler(initializer);
		
		try {
			channel = bootstarp.connect(host, port).sync().channel();
			success = true;
			
			LOG.info("connect successfully. host: {}, port: {}", host, port);
		} catch (InterruptedException e) {
			LOG.error("connect failed, host: {}, port: {}", host, port, e);

			throw new RuntimeException(e);
		}
	}

	public void stop() {
		if (success) {
			channel.disconnect();
		}
	}
	
	public void sendMessage(Object msg) {
		channel.writeAndFlush(msg);
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setInitializer(ChannelInitializer<SocketChannel> initializer) {
		this.initializer = initializer;
	}

}
