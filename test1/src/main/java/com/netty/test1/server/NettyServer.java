package com.netty.test1.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

  public static void main(String[] args) {
    EventLoopGroup boss = new NioEventLoopGroup();
    EventLoopGroup worker = new NioEventLoopGroup();
    try {
      ServerBootstrap serverBootstrap = new ServerBootstrap();
      serverBootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
        .childHandler(new MyServerInitializer());
      // 绑定端口，开始接收进来的连接
      ChannelFuture f = serverBootstrap.bind(8899).sync();

      // 等待服务器socket关闭
      f.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      boss.shutdownGracefully();
      worker.shutdownGracefully();
    }
  }

}
