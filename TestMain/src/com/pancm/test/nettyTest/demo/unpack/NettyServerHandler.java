package com.pancm.test.nettyTest.demo.unpack;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.InetAddress;

/**
 * 
* Title: HelloServerHandler
* Description:  服务端业务逻辑 粘包、拆包测试
* Version:1.0.0  
* @author pancm
* @date 2017年10月8日
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
	/** 条数 */
	private int count=0; 
	/**
     * 业务逻辑处理
     */
    @Override  
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
    	   String body = (String)msg;  
           System.out.println("接受的数据是: " + body + ";条数是: " + ++count); 
    }  

	/**
	 * 建立连接时，返回消息
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("连接的客户端地址:" + ctx.channel().remoteAddress());
		ctx.writeAndFlush("客户端"+ InetAddress.getLocalHost().getHostName() + "成功与服务端建立连接！ ");
		super.channelActive(ctx);
	}
}
