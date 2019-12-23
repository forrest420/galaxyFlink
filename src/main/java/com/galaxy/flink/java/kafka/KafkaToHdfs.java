package com.galaxy.flink.java.kafka;

import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

/**
 * @author wang.baozhi
 * @since 2019/12/17 下午7:22
 */
public class KafkaToHdfs {
    public static void main(String[] args) throws Exception {
        //init env
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //config checkpoint
        setCheckpoint(env);
        //env.setStateBackend();

        //get sourceFunction
        SourceFunction<String> source=generateSourceFunction();
        //add sourceFunction
        DataStream<String> dataStream = env.addSource(source).name("kafka_source").uid("kafka_source_id");


        //add mapFunction
        dataStream=dataStream.map(new KafkaValue()).name("mapFunction").uid("mapFunctionUid");

        //add sink
        SinkFunction sink= generateSinkFunction();
        dataStream.addSink(sink);


        // 打印数据流
        dataStream.print();

        // 直接写本地文件
        // dataStream.writeAsText(outputPath+ File.separator+"a.txt").setParallelism(1);

        //execute
        env.execute();
    }

    public static SinkFunction<String> generateSinkFunction(){
        DefaultRollingPolicy rollingPolicy = DefaultRollingPolicy
                .create()
                .withMaxPartSize(128 * 1024) // 设置每个文件的最大大小 ,默认是128M。这里设置为128KB
                .withRolloverInterval(60000) // 滚动写入新文件的时间，默认60s。这里设置为无限大
                .withInactivityInterval(60 * 1000) // 60s空闲，就滚动写入新的文件
                .build();

        String outputPath = "file:///Users/baozhiwang/tmp/flink";
        final StreamingFileSink<String> sink = StreamingFileSink
                .forRowFormat(new Path(outputPath), new SimpleStringEncoder<String>("UTF-8"))
                .withRollingPolicy(rollingPolicy)
                // .withBucketAssigner(new MemberBucketAssigner())
                //.withBucketCheckInterval(1000L) // 桶检查间隔，这里设置为1s
                .build();
        return sink;
    }

    public static SourceFunction<String> generateSourceFunction(){
        FlinkKafkaConsumer<String> kafkaConsumer = FlinkKafka.createStringConsumerForTopic(FlinkKafkaWindow.inputTopic, FlinkKafkaWindow.address, FlinkKafkaWindow.consumerGroup);
        kafkaConsumer.setStartFromLatest();
        return kafkaConsumer;
    }

    public static StreamExecutionEnvironment setCheckpoint(StreamExecutionEnvironment env){
        //default is false,unable
        // start a checkpoint every 1000 ms
        env.enableCheckpointing(1000);

        // advanced options:
        // set mode to exactly-once (this is the default)
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        // make sure 500 ms of progress happen between checkpoints
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(500);

        // checkpoints have to complete within one minute, or are discarded
        env.getCheckpointConfig().setCheckpointTimeout(60000);

        // allow only one checkpoint to be in progress at the same time
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);

        // enable externalized checkpoints which are retained after job cancellation
        env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);

        // allow job recovery fallback to checkpoint when there is a more recent savepoint
        env.getCheckpointConfig().setPreferCheckpointForRecovery(true);

        return env;
    }
}
