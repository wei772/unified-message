package cn.garden.message.util.cache;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author liwei
 */
@Service
public class MemoryCache<T> implements Cache<T> {

    private final Map<String, T> map = new HashMap<>();

    @Override
    public T get(String key) {
        return map.get(key);
    }

    @Override
    public void set(String key, T value, long timeout, TimeUnit unit) {
        map.put(key, value);
    }
}
