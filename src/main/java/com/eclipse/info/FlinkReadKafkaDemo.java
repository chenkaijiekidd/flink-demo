package com.eclipse.info;

import java.util.Properties;

import com.eclipse.info.pojo.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.formats.json.JsonNodeDeserializationSchema;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

/**
 *@ClassName FlinkReadKafkaDemo
 *Description 读取kafka中的数据并且通过json转换成对象
 *@Author kidd
 *@Date 2021/10/5 12:01 下午
 *Version 0.1
 **/
public class FlinkReadKafkaDemo {

	private static Properties kafkaProps = new Properties();
	static{
		kafkaProps.put("bootstrap.servers", "localhost:9092");
		kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	}

	public static void main(String[] args) throws Exception{
		ObjectMapper mapper = new ObjectMapper();

		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		DataStream<ObjectNode> stream = env.addSource(new FlinkKafkaConsumer<ObjectNode>("flink", new JsonNodeDeserializationSchema(), kafkaProps));
		stream.map(value -> {
			String name = value.get("name").asText();
			int age = value.get("age").asInt();

			User user = new User(name, age);
			System.out.println(user);
			return user;
		});

		stream.print();
		env.execute("Read from kafka as string");
	}
}
