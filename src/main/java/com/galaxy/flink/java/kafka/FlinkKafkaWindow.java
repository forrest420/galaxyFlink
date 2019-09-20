package com.galaxy.flink.java.kafka;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.connectors.kafka.KafkaSerializationSchema;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.annotation.Nullable;
import java.util.Properties;

/**
 * @author wang.baozhi
 * @since 2019/8/22 下午5:03
 */
public class FlinkKafkaWindow {

    //parameters
    static String inputTopic = "influxdb-player-sdk-startplay";
    static String outputTopic = "short-video-test";
    static String consumerGroup = "michael-group";
    static String address = "bigdata-appsvr-130-1:9095,bigdata-appsvr-130-2:9095,bigdata-appsvr-130-3:9095,bigdata-appsvr-130-4:9095,bigdata-appsvr-130-5:9095,bigdata-appsvr-130-6:9095,bigdata-appsvr-130-7:9095,bigdata-appsvr-130-8:9095,bigdata-appsvr-130-9:9095";

    public static void main(String[] args) throws Exception {


        //init env
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //init source
        FlinkKafkaConsumer<String> flinkKafkaConsumer = FlinkKafka.createStringConsumerForTopic(FlinkKafkaWindow.inputTopic, FlinkKafkaWindow.address, FlinkKafkaWindow.consumerGroup);
        flinkKafkaConsumer.setStartFromLatest();

        //init sink
        Properties productProperties = new Properties();
        productProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,FlinkKafkaWindow.address);
        FlinkKafkaProducer<String> flinkKafkaProducer = new FlinkKafkaProducer<String>(FlinkKafkaWindow.outputTopic,new KafkaSer(FlinkKafkaWindow.outputTopic),productProperties,FlinkKafkaProducer.Semantic.EXACTLY_ONCE);

        //algs
        DataStream<String> dataStream = env.addSource(flinkKafkaConsumer);
        dataStream.map(new KafkaValue()).addSink(flinkKafkaProducer);

        //execute
        env.execute();
    }


    private static class KafkaSer implements KafkaSerializationSchema<String>  {
        private String topic;
        public KafkaSer(String topic){
            this.topic=topic;
        }
        @Override
        public ProducerRecord<byte[], byte[]> serialize(
                String element, @Nullable Long timestamp) {
            return new ProducerRecord<>(topic, element.getBytes());
        }
    }

}
