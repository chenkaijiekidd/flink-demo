package com.eclipse.info;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *@ClassName JedisTest
 *Description TODO
 *@Author kidd
 *@Date 2021/10/10 8:07 上午
 *Version 0.1
 **/
public class JedisTest {

	public static void main(String[] args) {

		JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost", 6379);
		Jedis jedis = pool.getResource();
		pool.returnResource(jedis);
		System.out.println(jedis.get("flink"));
		pool.close();
	}
}
