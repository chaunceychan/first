package com.tiza.chen.spring.nio;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author:chen_gc@tiza.com.cn
 * @className:APHandler
 * @description:类的描述
 * @date:2014/5/29 15:27
 */
public class APHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(APHandler.class);
    private APConnector connector;
    private int spid;

    public APHandler(APConnector connector) {
        this.connector = connector;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("Socket连接异常", cause.getMessage());
        ctx.close();
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        int dataLen = buf.readInt();//长度
        System.out.println("接收到的数据长度" + dataLen + ",内容:" + buf.toString());
        ByteBuf data = ctx.alloc().buffer(5);
        data.writeInt(5);
        data.writeByte(10);
        ctx.writeAndFlush(data);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        final EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(new Runnable() {
            @Override
            public void run() {
                logger.info("SP与AP连接断开,正在重连中...");
                connector.configureBootstrap(new Bootstrap(), eventLoop).connect();
            }
        }, 30, TimeUnit.SECONDS);
    }

}
