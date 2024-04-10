package netty.pack;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;


public class encode {
    private static final int MAGIC_NUMBER = 0x12345678;

    public ByteBuf encoding(Packet packet){
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        byte[] bytes = Serializer.DEFAULT.serialize(packet);
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
        return byteBuf;

    }

//    public Packet decode(ByteBuf byteBuf){
//        byteBuf.skipBytes(4);
//        byteBuf.skipBytes(1);
//        byte SA = byteBuf.readByte();
//        byte command = byteBuf.readByte();
//        int length = byteBuf.readInt();
//        byte[] bytes = new byte[length];
//        byteBuf.readBytes(bytes);
//
//        Class<? extends Packet> requestType=getRequestType();
//

    }



