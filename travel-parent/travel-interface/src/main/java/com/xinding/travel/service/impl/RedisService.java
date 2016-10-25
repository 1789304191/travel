package com.xinding.travel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Service;

import com.xinding.travel.service.IRedisService;

import redis.clients.jedis.Jedis;

@Service
public class RedisService implements IRedisService {

	private static Jedis jedis;

	@Autowired
	@Qualifier("jedisConnectionFactory")
	private JedisConnectionFactory jedisConnectionFactory;

	private Jedis getJedis() {
		if (jedis == null) {
			 return jedisConnectionFactory.getShardInfo().createResource();
		}
		return jedis;
	}

	@Override
	public String get(String key) {
		String value = this.getJedis().get(key);
		return value;
	}

	@Override
	public Long setnx(String key, String value) {
		long val = this.getJedis().setnx(key, value);
		return val;
	}

	@Override
	public void set(String key, String value) {
		this.getJedis().set(key, value);
	}

	@Override
	public String getSet(String key, String value) {
		String val = this.getJedis().getSet(key, value);
		return val;
	}

	@Override
	public void del(String key) {
		this.getJedis().del(key);
	}

}
