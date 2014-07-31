package com.tiza.chen.spring.nio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author:chen_gc@tiza.com.cn
 * @className:ClientDecoder
 * @description:解码类,用于处理粘包问题
 * @date:2014/6/5 17:07
 */
public class ClientDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.markReaderIndex();
        if (in.readableBytes() >= 4) {
            int length = in.readInt();
            if (length == 0) {
                throw new Exception("Error:The data packet length is 0");
            } else {
                in.resetReaderIndex();
                if (in.readableBytes() >= length) {
                    out.add(in.readBytes(length));
                }
            }
        }
    }
}
