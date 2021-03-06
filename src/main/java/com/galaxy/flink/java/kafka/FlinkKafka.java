package com.galaxy.flink.java.kafka;

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
public class FlinkKafka {
    //parameters
    static String inputTopic = "flink_input";
    static String outputTopic = "flink_output";
    static String consumerGroup = "michael-group";
    static String address = "localhost:9092";

    public static FlinkKafkaConsumer<String> createStringConsumerForTopic(
            String topic, String kafkaAddress, String kafkaGroup) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", kafkaAddress);
        props.setProperty("group.id", kafkaGroup);
        FlinkKafkaConsumer<String> consumer =
                new FlinkKafkaConsumer<>(topic, new SimpleStringSchema(),props);
        return consumer;
    }

    public static void main(String[] args) throws Exception {
        //init env
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //init source
        FlinkKafkaConsumer<String> flinkKafkaConsumer = createStringConsumerForTopic(inputTopic, address, consumerGroup);
        flinkKafkaConsumer.setStartFromEarliest();

        //init sink
        Properties productProperties = new Properties();
        productProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,address);
        FlinkKafkaProducer<String> flinkKafkaProducer = new FlinkKafkaProducer<String>(outputTopic,new KafkaSer(outputTopic),productProperties,FlinkKafkaProducer.Semantic.EXACTLY_ONCE);

        //algs
        DataStream<String> dataStream = env.addSource(flinkKafkaConsumer);
        dataStream.map(new WordsCapitalizer()).addSink(flinkKafkaProducer);
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
