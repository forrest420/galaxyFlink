package com.galaxy.flink.java.kafka;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.fs.bucketing.BucketingSink;
import org.apache.flink.streaming.connectors.fs.bucketing.DateTimeBucketer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.time.ZoneId;
import java.util.Properties;

/**
 * @author wang.baozhi
 * @since 2019/12/17 下午7:22
 */
public class Kafka2Hdfs {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment flinkEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        // 获取Kafka配置
        Properties props = new Properties();
        //props.putAll(kafkaProperties.buildConsumerProperties());
        props.putAll(null);
        // 创建Kafka-Source
        FlinkKafkaConsumer<String> consumer = null;
        //FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<>(KafkaTopic.TRACK_LOGS, new SimpleStringSchema(), props);
        // 添加Kafka-Source
        DataStreamSource<String> source = flinkEnv.addSource(consumer);
        // 方式1：将数据导入Hadoop的文件夹
        //recordData.writeAsText("hdfs://hadoop:9000/flink/");
        // 方式2：将数据导入Hadoop的文件夹
        BucketingSink<String> hadoopSink = new BucketingSink<>("hdfs://hadoop:9000/flink/");
        // 使用东八区时间格式"yyyy-MM-dd--HH"命名存储区
        hadoopSink.setBucketer(new DateTimeBucketer<>("yyyy-MM-dd--HH", ZoneId.of("Asia/Shanghai")));
        // 下述两种条件满足其一时，创建新的块文件
        // 条件1.设置块大小为100MB
        hadoopSink.setBatchSize(1024 * 1024 * 100);
        // 条件2.设置时间间隔20min
        hadoopSink.setBatchRolloverInterval(20 * 60 * 1000);
        // 设置块文件前缀
        hadoopSink.setPendingPrefix("");
        // 设置块文件后缀
        hadoopSink.setPendingSuffix("");
        // 设置运行中的文件前缀
        hadoopSink.setInProgressPrefix(".");
        // 添加Hadoop-Sink,处理相应逻辑
        source.addSink(hadoopSink);

    }
}
