package com.eclipse.info;

import java.util.Properties;

import com.eclipse.info.pojo.User;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.kafka.connect.json.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 *@ClassName KafkaProducer
 *Description TODO
 *@Author kidd
 *@Date 2021/10/5 8:32 下午
 *Version 0.1
 **/
public class KafkaJsonProducer {

	public static void main(String[] args) {

		String TOPIC = "flink";
		String BOOTSTRAP_SERVERS = "localhost:9092";

		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
		props.put(ProducerConfig.ACKS_CONFIG, "1");

		final KafkaProducer<String, JsonNode> producer = new KafkaProducer<String, JsonNode>(props);
		User user = new User("example", new Integer(20));
		User user2 = new User("another", new Integer(21));

		ObjectMapper mapper = new ObjectMapper();
		producer.send(new ProducerRecord<String, JsonNode>(TOPIC, user.getName(), mapper.valueToTree(user)));
		producer.send(new ProducerRecord<String, JsonNode>(TOPIC, user2.getName(), mapper.valueToTree(user)));

		producer.close();


	}
}
