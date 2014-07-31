package com.tiza.chen.spring.nio;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author:chen_gc@tiza.com.cn
 * @className:APConnector
 * @description:用于管理SP到AP的连接
 * @date:2014/6/5 17:30
 */
public class APConnector {
    private Logger logger = LoggerFactory.getLogger(APConnector.class);
    private final InetSocketAddress address;
    private final APHandler handler = new APHandler(this);

    public APConnector(InetSocketAddress address) {
        this.address = address;
    }

    public void run() {
        try {
            ChannelFuture f = configureBootstrap(new Bootstrap()).connect().sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("Socket连接中断异常", e.getMessage());
        }
    }

    private Bootstrap configureBootstrap(Bootstrap b) {
        return configureBootstrap(b, new NioEventLoopGroup());
    }

    public Bootstrap configureBootstrap(Bootstrap b, EventLoopGroup g) {

        b.group(g).channel(NioSocketChannel.class).remoteAddress(address).option(ChannelOption.TCP_NODELAY, true).option(ChannelOption.SO_KEEPALIVE, true).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast("decoder", new ClientDecoder());
                pipeline.addLast("encoder", new ClientEncoder());
                pipeline.addLast("handler", handler);
            }
        });

        return b;
    }
}