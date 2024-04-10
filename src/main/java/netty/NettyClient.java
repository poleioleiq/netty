package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import javax.xml.crypto.Data;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class NettyClient {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        System.out.println("连接成功");
                        ch.pipeline().addLast(new MyHandler());
                    }
                }).connect("127.0.0.1",8000);
    }
}
class MyHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("客户端发送消息");

        Channel channel = ctx.channel();
        ByteBuf byteBuf = getByteBuf(ctx);
        channel.writeAndFlush(byteBuf);
    }
    private static ByteBuf getByteBuf(ChannelHandlerContext ctx){
        ByteBuf buffer = ctx.alloc().buffer();
        byte[] bytes = ("这里是" + Thread.currentThread().getName() + "  客户端上线：hello world!").getBytes(StandardCharsets.UTF_8);
        buffer.writeBytes(bytes);
        return buffer;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        ByteBuf msg1 = (ByteBuf) msg;
        String s = (new Date() + "  客户端读到数据  -->\n" )+ msg1.toString(StandardCharsets.UTF_8);
        System.out.println(s);
    }
}

