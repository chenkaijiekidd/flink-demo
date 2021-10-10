package com.eclipse.info;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.async.ResultFuture;
import org.apache.flink.streaming.api.functions.async.RichAsyncFunction;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *@ClassName AsyncRedis
 *Description TODO
 *@Author kidd
 *@Date 2021/10/9 10:23 下午
 *Version 0.1
 **/
public class AsyncRedis extends RichAsyncFunction<String, String> {

	private transient JedisPool pool;

	@Override
	public void open(Configuration parameters) throws Exception {
		super.open(parameters);
		pool = new JedisPool(new JedisPoolConfig(), "localhost", 6379);
		System.out.println("open:" + pool);
	}

	@Override
	public void close() throws Exception {
		super.close();
		pool.close();
		System.out.println("close");
	}

	@Override
	public void asyncInvoke(String key, ResultFuture<String> resultFuture) throws Exception {

		CompletableFuture.supplyAsync(new Supplier<String>() {

			String result = null;
			@Override
			public String get() {

					//ObjectMapper mapper = new ObjectMapper();
					//User u = mapper.readValue(input, User.class);

					Jedis jedis = pool.getResource();
					pool.returnResource(jedis);
					result = jedis.get(key);
					System.out.println(result);
					return result;
			}});
	}

}
