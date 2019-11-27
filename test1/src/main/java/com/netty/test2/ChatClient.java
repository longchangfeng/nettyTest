package com.netty.test2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ChatClient {
  public static void main(String[] args) {
    EventLoopGroup client = new NioEventLoopGroup();
    try {
      Bootstrap bootstrap = new Bootstrap();
      bootstrap.group(client).channel(NioSocketChannel.class).handler(new MyChatClientInitializer());
      Channel channel = bootstrap.connect("localhost", 8899).sync().channel();
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
      while (true) {
        channel.writeAndFlush(bufferedReader.readLine() + "\n");
      }
    } catch (InterruptedException | IOException e) {
      e.printStackTrace();
    } finally {
      client.shutdownGracefully();
    }
  }
}
