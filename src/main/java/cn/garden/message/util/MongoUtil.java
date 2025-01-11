package cn.garden.message.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.util.Properties;

/**
 * Mongo 工具类
 */
public class MongoUtil {

    private static final Properties properties;

    static {
        properties = PropertiesUtil.getProperties(
                "application.properties"
                , "mongodb.properties");
    }


    private static String getUri() {
        return properties.getProperty("spring.data.mongodb.uri");
    }

    private static String getHost() {
        return properties.getProperty("spring.data.mongodb.host");
    }

    private static Integer getPort() {
        return Integer.parseInt(properties.getProperty("spring.data.mongodb.port"));
    }

    private static String getDatabase() {
        return properties.getProperty("spring.data.mongodb.database");
    }

    private static String getMongoDBUri() {
        String uri = getUri();
        if (StringUtils.isNotEmpty(uri)) {
            return uri;
        }
        return "mongodb://"
                + getHost()
                + ":"
                + getPort()
                + "/"
                + getDatabase();
    }

    private static MongoDatabaseFactory getMongoDatabaseFactory() {
        return new SimpleMongoClientDatabaseFactory(getMongoDBUri());
    }

    public static MongoTemplate getMongoTemplate(MongoDatabaseFactory mongoDbFactory) {
        return new MongoTemplate(mongoDbFactory);
    }

    public static MongoTemplate getMongoTemplate() {
        return getMongoTemplate(getMongoDatabaseFactory());
    }

}
