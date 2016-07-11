package com.s4game.core.net;

import java.util.List;

import com.google.protobuf.MessageLite;
import com.s4game.core.message.mapping.MessageMapping;
import com.s4game.core.net.codec.protobuf.ProtobufDecoder;
import com.s4game.core.net.codec.protobuf.ProtobufEncoder;

import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * 
 * @Author zeusgooogle@gmail.com
 * @sine 2016年7月7日 上午10:43:25
 * 
 */
public class NetInitializer extends ChannelInitializer<SocketChannel> {

    private List<ChannelInboundHandler> handlers;

    private MessageMapping<MessageLite> messageMapping;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // decoder
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        pipeline.addLast(new ProtobufDecoder(messageMapping));

        // encoder
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast(new ProtobufEncoder(messageMapping));

        for (ChannelInboundHandler h : handlers) {
            pipeline.addLast("handler" + h.getClass().getSimpleName(), h);
        }
    }

    public void setHandlers(List<ChannelInboundHandler> handlers) {
        this.handlers = handlers;
    }

    public List<ChannelInboundHandler> getHandlers() {
        return handlers;
    }

    public MessageMapping<MessageLite> getMessageMapping() {
        return messageMapping;
    }

    public void setMessageMapping(MessageMapping<MessageLite> messageMapping) {
        this.messageMapping = messageMapping;
    }

}
