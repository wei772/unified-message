package cn.garden.message.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class PropertiesUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);

    public static Properties getProperties(String... resourceNames) {
        Properties properties = new Properties();
        for (String resourceName : resourceNames) {
            try {
                InputStream inputStream = PropertiesUtil.class.getClassLoader()
                        .getResourceAsStream(resourceName);
                if (Objects.isNull(inputStream)) {
                    continue;
                }
                properties.load(inputStream);
            } catch (IOException e) {
                LOGGER.warn("加载配置文件失败" + resourceName, e);
            }
        }
        return properties;
    }


    public static Map<String, String> getPropertiesMap(String... resourceNames) {
        return getPropertiesMap(getProperties(resourceNames));
    }

    public static Map<String, String> getPropertiesMap(Properties properties) {
        Map<String, String> map = new HashMap<>();
        for (Object key : properties.keySet()) {
            map.put((String) key, properties.getProperty((String) key));
        }
        return map;
    }
}
