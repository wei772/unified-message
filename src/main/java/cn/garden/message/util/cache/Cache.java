package cn.garden.message.util.cache;

import java.util.concurrent.TimeUnit;

/**
 * 缓存接口
 *
 * @author liwei
 */
public interface Cache<V> {

    V get(String key);

    void set(String key, V value, long timeout, TimeUnit unit);
}
