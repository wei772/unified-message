package cn.garden.message.util.cache;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * windows下使用redis不方便，暂时不启用
 *
 * @author liwei
 */

public class RedisCache<T> implements Cache<T> {

    private final RedisTemplate<String, T> redisTemplate;

    public RedisCache(RedisTemplate<String, T> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public T get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void set(String key, T value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key,
                value,
                timeout,
                unit);
    }
}
