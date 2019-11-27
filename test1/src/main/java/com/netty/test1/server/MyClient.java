package com.netty.test1.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyClient {
  public static void main(String[] args) {
    EventLoopGroup client = new NioEventLoopGroup();
    try {
      Bootstrap bootstrap = new Bootstrap();
      bootstrap.group(client).channel(NioSocketChannel.class).handler(new MyClientInitializer());
      ChannelFuture future = bootstrap.connect("localhost", 8899).sync();
      future.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      client.shutdownGracefully();
    }
  }
}
