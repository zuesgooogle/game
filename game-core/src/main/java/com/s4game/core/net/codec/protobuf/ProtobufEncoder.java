package com.s4game.core.net.codec.protobuf;

import java.util.List;

import com.google.protobuf.MessageLite;
import com.s4game.core.message.mapping.MessageMapping;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

@Sharable
public class ProtobufEncoder extends MessageToMessageEncoder<MessageLite> {

    private MessageMapping<MessageLite> messageMapping;

    public ProtobufEncoder(MessageMapping<MessageLite> messageMapping) {
        this.messageMapping = messageMapping;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageLite msg, List<Object> out) throws Exception {
        // messageId + body
        int messageId = messageMapping.getMessageId(msg.getClass());
        
        ByteBuf messageHead = Unpooled.buffer(2);
        messageHead.writeShort(messageId);
        
        out.add(Unpooled.wrappedBuffer(messageHead.array(), ((MessageLite) msg).toByteArray()));
    }
}
