package com.netty.test2;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {
  private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

  @Override

  protected void channelRead0(ChannelHandlerContext ctx, String msg) {
    Channel channel = ctx.channel();
    channelGroup.forEach((x) -> {
      if (x != channel) {
        x.writeAndFlush(x.remoteAddress() + "发送的消息: " + msg + "\n");
      } else {
        x.writeAndFlush("[自己]" + msg + "\n");
      }
    });
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }

  @Override
  public void handlerAdded(ChannelHandlerContext ctx) {
    Channel channel = ctx.channel();
    channelGroup.writeAndFlush("[服务器] -" + channel.remoteAddress() + " 加入" + "\n");
    channelGroup.add(channel);
  }

  @Override
  public void handlerRemoved(ChannelHandlerContext ctx) {
    Channel channel = ctx.channel();
    channelGroup.writeAndFlush("[服务器] -" + channel.remoteAddress() + " 离开" + "\n");
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("[服务器] -" + ctx.channel().remoteAddress() + " 上线了");
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("[服务器] -" + ctx.channel().remoteAddress() + " 下线了");
  }
}
