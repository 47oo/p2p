package com.p2p.server;

import com.p2p.handler.HttpHandler;
import com.p2p.handler.WebSocketHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class ChatServer {

	private int port = 2222;

	public void start() {
		// 主从模式、创建主线程
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		// 创建子线程，工作线程
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		//创建Netty Socket Server 
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024).childHandler(new ChannelInit());
			ChannelFuture f = b.bind(port).sync();
			System.out.println("监听端口已经启动:"+port);
			f.channel().closeFuture().sync();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
		}
	}

	private class ChannelInit extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			// 获取工作流
			ChannelPipeline pipeline = ch.pipeline();

			// ======== 对HTTP协议的支持 ==========
			// Http请求解码器
			pipeline.addLast(new HttpServerCodec());
			// 主要就是将一个http请求或者响应变成一个FullHttpRequest对象
			pipeline.addLast(new HttpObjectAggregator(64 * 1024));
			// 这个是用来处理文件流
			pipeline.addLast(new ChunkedWriteHandler());
			// 处理HTTP请求的业务逻辑
			pipeline.addLast(new HttpHandler());
			// ======== 对WebSocket协议的支持 ==========
			// 加上这个Handler就已经能够解析WebSocket请求了
			// 相当于WebSocket解码器
			// im是为了和http请求 区分开来，以im开头的请求都有websocket来解析
			pipeline.addLast(new WebSocketServerProtocolHandler("/p2p/im"));
			// 实现处理WebSocket逻辑的Handler
			pipeline.addLast(new WebSocketHandler());
		}

	}
	public static void main(String[] args) {
		new ChatServer().start();
	}
}
