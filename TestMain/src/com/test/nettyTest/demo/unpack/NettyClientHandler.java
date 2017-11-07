package com.test.nettyTest.demo.unpack;

import java.util.Date;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 
* Title: NettyClientHandler
* Description: 客户端业务逻辑实现
* Version:1.0.0  
* @author pancm
* @date 2017年10月8日
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

	 /**
     * 业务逻辑处理	
     */
    @Override  
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
    	System.out.println("客户端接受的消息:"+msg);
    }  
    /**
     * 建立连接时
     */
    @Override  
    public void channelActive(ChannelHandlerContext ctx) throws Exception {  
        System.out.println("建立连接时："+new Date());  
        ctx.fireChannelActive();  
    }  
  
     /**
      * 
      * 关闭连接时
      */
    @Override  
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {  
        System.out.println("关闭连接时："+new Date());  
    }  
}
