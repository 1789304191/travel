package com.xinding.travel.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {
	public static JedisPool pool = new JedisPool(new JedisPoolConfig(), "192.168.1.149");
	public static Jedis jedis= pool.getResource();
}
