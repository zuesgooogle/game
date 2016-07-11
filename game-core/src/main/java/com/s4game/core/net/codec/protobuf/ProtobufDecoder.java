package com.s4game.core.net.codec.protobuf;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.MessageLite;
import com.s4game.core.exception.MessageNotFoundException;
import com.s4game.core.message.mapping.MessageMapping;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

/**
 * 通过 MessageMapping 获取对应的 MessageLite 进行解析
 * 
 * @author zeusgooogle@gmail.com
 * @sine 2016年7月6日 下午3:23:18
 */
@Sharable
public class ProtobufDecoder extends MessageToMessageDecoder<ByteBuf> {

    private Logger LOG = LoggerFactory.getLogger(getClass());
    
    private MessageMapping<MessageLite> messageMapping;
    
    public ProtobufDecoder(MessageMapping<MessageLite> messageMapping) {
        this.messageMapping = messageMapping;
    }
    
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        final int messageId = msg.readUnsignedShort();

        final int length = msg.readableBytes();
        final byte[] array = new byte[length];
        
        msg.getBytes(msg.readerIndex(), array, 0, length);
        
        MessageLite prototype = messageMapping.getPrototype(messageId);
        if (prototype == null) {
            throw new MessageNotFoundException("Message id: %d not found prototype.", messageId);
        }

        out.add(prototype.getParserForType().parseFrom(array, 0, length));
        
        if (LOG.isDebugEnabled()) {
            LOG.debug("Receive message id: {}, name: {}, bytes: {}", messageId, prototype.getClass().getName(), length);
        }
    }
}
