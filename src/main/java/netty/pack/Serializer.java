package netty.pack;

//import com.alibaba.fastjson.serializer.JSONSerializer;

import io.netty.buffer.ByteBuf;

public interface Serializer {
    byte getSerializerAlgorithm();
    byte[] serialize(Object object);
    <T>T deserialize(Class<T> clazz,byte[] bytes);

    byte JSON_SERIALIZER=1;
    Serializer DEFAULT=new JSONSerializer();
}
