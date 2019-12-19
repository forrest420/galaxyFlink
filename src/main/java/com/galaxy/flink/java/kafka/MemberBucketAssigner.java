package com.galaxy.flink.java.kafka;


import org.apache.flink.core.io.SimpleVersionedSerializer;
import org.apache.flink.streaming.api.functions.sink.filesystem.BucketAssigner;
import org.apache.flink.streaming.api.functions.sink.filesystem.bucketassigners.SimpleVersionedStringSerializer;
/**
 * @author wang.baozhi
 * @since 2019/12/18 上午10:12
 */


public class MemberBucketAssigner implements BucketAssigner<String, String> {
    private static final long serialVersionUID = 10000L;

    @Override
    public String getBucketId(String info, Context context) {
        // 指定桶名 yyyy-mm-dd
        //String[] array = info.split("\t");
        return "aaa";
        /*System.out.println(DateUtil.format(new Date(Long.valueOf(array[5])), "yyyy-MM-dd"));
        return DateUtil.format(new Date(Long.valueOf(array[5])), "yyyy-MM-dd");*/
    }

    @Override
    public SimpleVersionedSerializer<String> getSerializer() {
        return SimpleVersionedStringSerializer.INSTANCE;
    }
}
