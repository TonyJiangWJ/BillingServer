package com.tony.billing.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class RedisUtils {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static StringRedisTemplate redisTemplate;

    @PostConstruct
    public void init() {
        redisTemplate = stringRedisTemplate;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        RedisUtils.logger = logger;
    }

    private static Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    // 设置对象持久化
    public static boolean set(final Object key, final Object val) {
        boolean result = false;
        try {
            result = redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                    byte[] keys = serializer.serialize(JSON.toJSONString(key));
                    byte[] name = serializer.serialize(JSON.toJSONString(val));
                    return connection.setNX(keys, name);
                }
            });
        } catch (Exception e) {
            logger.error("设置cache错误", e);
        }

        return result;
    }

    // 设置对象存活时间
    public static void set(final Object key, final Object val, final long time) {
        try {
            redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                    byte[] keys = serializer.serialize(JSON.toJSONString(key));
                    byte[] name = serializer.serialize(JSON.toJSONString(val));
                    connection.setEx(keys, time, name);
                    return null;
                }
            });
        } catch (Exception e) {
            logger.error("设置定时cache错误", e);
        }

    }

    // 获取对象
    public static Map<Object, Object> get(final Object key, final Class<?> clazz) {
        Map<Object, Object> result = null;
        try {
            result = redisTemplate.execute(new RedisCallback<Map<Object, Object>>() {
                public Map<Object, Object> doInRedis(RedisConnection connection) throws DataAccessException {
                    RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                    byte[] keys = serializer.serialize(JSON.toJSONString(key));
                    byte[] value = connection.get(keys);
                    if (value == null) {
                        return null;
                    }
                    String jsonString = serializer.deserialize(value);
                    Map<Object, Object> map = new HashMap<Object, Object>();
                    if (jsonString.startsWith("{"))
                        map.put(key, JSON.parseObject(jsonString, clazz));
                    else
                        map.put(key, jsonString);
                    return map;
                }
            });
        } catch (Exception e) {
            logger.error("设置定时cache错误", e);
        }

        return result;
    }

    // 计数器
    public static Long incr(final Object key, final Long by) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] keys = serializer.serialize(JSON.toJSONString(key));
                return connection.incrBy(keys, by == null ? 1 : by);
            }
        });
    }

    // 删除对象
    public static boolean del(final Object key) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] keys = serializer.serialize(JSON.toJSONString(key));
                return connection.get(keys) == null || connection.del(keys) > 0;
            }
        });
    }
}
