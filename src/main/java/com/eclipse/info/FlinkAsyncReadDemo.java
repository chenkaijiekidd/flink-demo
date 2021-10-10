package com.eclipse.info;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.AsyncDataStream;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

/**
 *@ClassName FlinkAsyncRead
 *Description TODO
 *@Author kidd
 *@Date 2021/10/5 12:01 下午
 *Version 0.1
 **/
public class FlinkAsyncReadDemo {

	private static Properties kafkaProps = new Properties();
	static{
		kafkaProps.put("bootstrap.servers", "localhost:9092");
		kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	}

	public static void main(String[] args) throws Exception{
		ObjectMapper mapper = new ObjectMapper();

		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		DataStream<String> stream = env.addSource(new FlinkKafkaConsumer<String>("flink", new SimpleStringSchema(), kafkaProps));
		DataStream<String> resultStream = AsyncDataStream.unorderedWait(stream, new AsyncRedis(),10000, TimeUnit.MICROSECONDS, 100);
		resultStream.print();

//DataStream<ObjectNode> stream = env.addSource(new FlinkKafkaConsumer<ObjectNode>("flink", new JsonNodeDeserializationSchema(), kafkaProps));
		/*		stream.map(value -> {
			String name = value.get("name").asText();
			int age = value.get("age").asInt();

			User user = new User(name, age);
			System.out.println(user);
			return user;
		});*/

		//stream.print();
		env.execute("Read from kafka as string");
	}
}
