package cn.garden.message.client.wecom.api;

import cn.garden.message.util.FeignUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liwei
 */
@Configuration
public class WecomServiceFactory {

    public static WecomService createDefault() {
        return createMockService();
    }

    /**
     * 创建微信服务
     */
    @Bean
    public static WecomService createService() {
        return FeignUtil.createJsonClient(WecomService.class, "https://qyapi.weixin.qq.com");
    }

    /**
     * 创建微信服务
     */
    public static WecomService createMockService() {
        return new MockWecomService();
    }
}
