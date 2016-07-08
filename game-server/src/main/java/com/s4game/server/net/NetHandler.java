package com.s4game.server.net;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.MessageLite;
import com.s4game.protocol.Message.Response;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 
 * @Author zeusgooogle@gmail.com
 * @sine 2016年7月7日 下午7:10:58
 * 
 */
@Sharable
public class NetHandler extends SimpleChannelInboundHandler<MessageLite> {

	private Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		LOG.info("channel active: " + ctx.toString());
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MessageLite msg) throws Exception {
		LOG.info("server receive message: {}", msg.toString());
		
		Response response = Response.newBuilder()
		                        .setCommand(System.currentTimeMillis() + "")
		                        .setData("server response")
		                        .build();
		
		ctx.channel().writeAndFlush(response);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

	}

}
