package cn.garden.message.client.user.implementation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liwei
 */
@Configuration
public class UserClientFactory {

    public static MockUserClient createDefault() {
        return createMockUserClient();
    }

    @Bean
    public static MockUserClient createMockUserClient() {
        return new MockUserClient();
    }

}
