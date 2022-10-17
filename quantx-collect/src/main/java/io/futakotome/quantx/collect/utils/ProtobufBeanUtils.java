package io.futakotome.quantx.collect.utils;

import com.google.gson.Gson;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;

import java.util.Objects;

public final class ProtobufBeanUtils {

    public static <T> T protobuf2Bean(Class<T> destBeanClz, Message sourceMessage) throws InvalidProtocolBufferException {
        Objects.requireNonNull(destBeanClz);
        Objects.requireNonNull(sourceMessage);
        String json = JsonFormat.printer().print(sourceMessage);
        return new Gson().fromJson(json, destBeanClz);
    }
}
