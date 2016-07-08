package com.s4game.client.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.MessageLite;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 *
 * @Author zeusgooogle@gmail.com
 * @sine 2015年5月4日 上午10:48:43
 *
 */
@Sharable
public class ClientHandler extends SimpleChannelInboundHandler<MessageLite> {

    private Logger LOG = LoggerFactory.getLogger(getClass());

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageLite msg) throws Exception {
        LOG.info("client receive msg: {}", msg.toString());
    }

}
