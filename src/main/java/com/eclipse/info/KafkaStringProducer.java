package com.eclipse.info;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 *@ClassName KafkaStringProducer
 *Description TODO
 *@Author kidd
 *@Date 2021/10/10 9:41 上午
 *Version 0.1
 **/
public class KafkaStringProducer {

	public static void main(String[] args) {

		String TOPIC = "flink";
		String BOOTSTRAP_SERVERS = "localhost:9092";

		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.ACKS_CONFIG, "1");

		final KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
		producer.send(new ProducerRecord(TOPIC, "flink","flink"));

		producer.close();


	}
}
