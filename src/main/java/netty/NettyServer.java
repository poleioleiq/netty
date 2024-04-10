package netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss,worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MyServerHandler());
                    }
                }).bind(8000).addListener(new GenericFutureListener<Future<? super Void>>() {
                    @Override
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        System.out.println("绑定成功");
                    }
                });
    }
}
class MyServerHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);\
        ByteBuf byteBuf = getByteBuf(ctx);
        ctx.channel().writeAndFlush(byteBuf);

    }
    public static ByteBuf getByteBuf(ChannelHandlerContext ctx){
        ByteBuf buffer = ctx.alloc().buffer();
        ByteBuf byteBuf = buffer.writeBytes((new Date() + "  你好欢迎光临聊天室").getBytes());
        return buffer;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        ByteBuf msg1 = (ByteBuf) msg;
        System.out.println(new Date()+"  服务器读到数据  -->\n"+msg1.toString(StandardCharsets.UTF_8));
    }
}
