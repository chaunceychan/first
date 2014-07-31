package com.tiza.chen.spring.nio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author:chen_gc@tiza.com.cn
 * @className:ClientEncoder
 * @description:编码类
 * @date:2014/6/5 17:06
 */
public class ClientEncoder extends MessageToByteEncoder<ByteBuf> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
        if (null == msg) {
            return;
        }
        out.writeBytes(msg);
    }
}
