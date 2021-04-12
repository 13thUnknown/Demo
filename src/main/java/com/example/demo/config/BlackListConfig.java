package com.example.demo.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class BlackListConfig {

    @Bean
    public LoadingCache<String, Integer> tokenBlacklist(){
        LoadingCache<String, Integer> cache = CacheBuilder.newBuilder().
                expireAfterWrite(15, TimeUnit.MINUTES).build(new CacheLoader<>() {
            public Integer load(String key) {
                return 0;
            }
        });
        return cache;
    }
}
