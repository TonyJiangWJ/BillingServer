package com.tony.billing.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author tonyjiang
 */
@Component
public class RedisUtils {


    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RedisUtils(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private static Logger logger = LoggerFactory.getLogger(RedisUtils.class);


    /**
     * 设置对象持久化
     *
     * @param key 键值
     * @param val 内容
     * @return
     */
    public boolean set(final Object key, final Object val) {
        boolean result = false;
        try {
            result = stringRedisTemplate.execute((RedisCallback<Boolean>) connection -> {
                RedisSerializer<String> serializer = stringRedisTemplate.getStringSerializer();
                byte[] keys = serializer.serialize(JSON.toJSONString(key));
                byte[] name = serializer.serialize(JSON.toJSONString(val));
                return connection.setNX(keys, name);
            });
        } catch (Exception e) {
            logger.error("设置cache错误", e);
        }

        return result;
    }

    /**
     * 设置对象存活时间,单位秒
     *
     * @param key  键值
     * @param val  内容
     * @param time 过期时间
     */
    public void set(final Object key, final Object val, final long time) {
        try {
            stringRedisTemplate.execute((RedisCallback<Boolean>) connection -> {
                RedisSerializer<String> serializer = stringRedisTemplate.getStringSerializer();
                byte[] keys = serializer.serialize(JSON.toJSONString(key));
                byte[] name = serializer.serialize(JSON.toJSONString(val));
                connection.setEx(keys, time, name);
                return true;
            });
        } catch (Exception e) {
            logger.error("设置定时cache错误", e);
        }

    }


    /**
     * 获取对象
     *
     * @param key   键值
     * @param clazz 对象类
     * @return 返回键值对
     */
    public <E> Optional<E> get(final Object key, Class<E> clazz) {
        Optional<E> result = Optional.empty();
        try {
            result = stringRedisTemplate.execute((RedisCallback<Optional<E>>) connection -> {
                RedisSerializer<String> serializer = stringRedisTemplate.getStringSerializer();
                byte[] keys = serializer.serialize(JSON.toJSONString(key));
                byte[] value = connection.get(keys);
                if (value == null) {
                    return Optional.empty();
                }
                String jsonString = serializer.deserialize(value);
                if (clazz != null) {
                    return Optional.of(JSON.parseObject(jsonString, clazz));
                }
                return Optional.empty();
            });
        } catch (Exception e) {
            logger.error("获取缓存信息失败", e);
        }

        return result;
    }

    /**
     * 递增计数器
     *
     * @param key 键值
     * @param by  增加值
     * @return 增加后的值
     */
    public Long incr(final Object key, final Long by) {
        return stringRedisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> serializer = stringRedisTemplate.getStringSerializer();
            byte[] keys = serializer.serialize(JSON.toJSONString(key));
            return connection.incrBy(keys, by == null ? 1 : by);
        });
    }

    /**
     * 删除对象
     *
     * @param key 键
     */
    public boolean del(final Object key) {
        return stringRedisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = stringRedisTemplate.getStringSerializer();
            byte[] keys = serializer.serialize(JSON.toJSONString(key));
            return connection.get(keys) == null || connection.del(keys) > 0;
        });
    }
}
