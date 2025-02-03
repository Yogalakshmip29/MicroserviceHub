package com.example.cache;

import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
@EnableCaching
public class Caching {
	
	@Bean
public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
	RedisCacheConfiguration redisCacheConfiguration=RedisCacheConfiguration.defaultCacheConfig()
			.entryTtl(Duration.ofMinutes(60)).disableCachingNullValues();
	
	return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(redisCacheConfiguration).transactionAware().build();
}
}
