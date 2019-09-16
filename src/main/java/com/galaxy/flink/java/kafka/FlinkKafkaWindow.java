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
public class FlinkKafkaWindow {


    public static void main(String[] args) throws Exception {


        //init env
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //init source
        FlinkKafkaConsumer<String> flinkKafkaConsumer = FlinkKafka.createStringConsumerForTopic(FlinkKafka.inputTopic, FlinkKafka.address, FlinkKafka.consumerGroup);
        flinkKafkaConsumer.setStartFromEarliest();

        //init sink
        Properties productProperties = new Properties();
        productProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,FlinkKafka.address);
        FlinkKafkaProducer<String> flinkKafkaProducer = new FlinkKafkaProducer<String>(FlinkKafka.outputTopic,new KafkaSer(FlinkKafka.outputTopic),productProperties,FlinkKafkaProducer.Semantic.EXACTLY_ONCE);

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
