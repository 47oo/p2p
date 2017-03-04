package com.p2p.handler;

import com.p2p.processor.MsgProcessor;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	private MsgProcessor processor = new MsgProcessor();
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		processor.sendMsg(ctx.channel(), msg.text());
	}
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  //(5)
		processor.logout(ctx.channel());
	}

}
