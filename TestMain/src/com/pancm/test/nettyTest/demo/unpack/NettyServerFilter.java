package com.pancm.test.nettyTest.demo.unpack;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
 
/**
  * 
 * Title: HelloServerInitializer
 * Description: Netty 服务端过滤器
 * Version:1.0.0  
* @author pancm
* @date 2017年10月8日
  */
public class NettyServerFilter extends ChannelInitializer<SocketChannel> {
 
     @Override
     protected void initChannel(SocketChannel ch) throws Exception {
         ChannelPipeline ph = ch.pipeline();
         // 解码和编码，应和客户端一致
//       ph.addLast(new FixedLengthFrameDecoder(100));   //定长数据帧的解码器 ，每帧数据100个字节就切分一次。  用于解决粘包问题           
//         ph.addLast(new LineBasedFrameDecoder(2048));		//字节解码器 ,其中2048是规定一行数据最大的字节数。  用于解决拆包问题
         ph.addLast("aggregator", new HttpObjectAggregator(10*1024*1024)); 
         ph.addLast("decoder", new StringDecoder());
         ph.addLast("encoder", new StringEncoder());
         ph.addLast("handler", new NettyServerHandler());// 服务端业务逻辑
     }
 }
