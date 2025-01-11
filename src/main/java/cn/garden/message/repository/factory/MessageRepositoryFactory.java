package cn.garden.message.repository.factory;

import cn.garden.message.repository.MessageRepository;
import cn.garden.message.repository.implementation.MemoryMessageRepository;
import cn.garden.message.repository.implementation.MongoMessageRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author liwei
 */
@Configuration
public class MessageRepositoryFactory {

    /**
     * 创建默认
     */
    public static MessageRepository createDefault() {
        return createMemory();
    }

    /**
     * 创建内存
     */
    public static MessageRepository createMemory() {
        return MemoryMessageRepository.getInstance();
    }

    /**
     * 创建内存
     */
    @Bean
    public static MessageRepository createMongo(MongoTemplate mongoTemplate) {
        return new MongoMessageRepository(mongoTemplate);
    }
}
