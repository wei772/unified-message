package cn.garden.message.client.sms.blacktel.api;

import cn.garden.message.util.FeignUtil;
import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liwei
 */
@Configuration
public class BlacktelServiceFactory {

    @Value("${sender.blacktel.host}")
    private String blacktel_host;

    public static BlacktelSendService createDefaultSendService() {
        return createMockSendService();
    }

    public static BlacktelStatusService createDefaultStatusService() {
        return createMockStatusService();
    }

    public static BlacktelSendService createMockSendService() {
        return new MockBlacktelSendService();
    }

    public static BlacktelStatusService createMockStatusService() {
        return new MockBlacktelStatusService();
    }


    /**
     * 创建BlacktelSendService
     */
    public static BlacktelSendService createSendService(String url) {
        return FeignUtil.createJsonClient(BlacktelSendService.class, url);
    }

    public static BlacktelStatusService createStatusService(String url) {
        Slf4jLogger slf4jLogger = new Slf4jLogger(BlacktelStatusService.class);
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .logger(slf4jLogger)
                .logLevel(Logger.Level.FULL)
                .target(BlacktelStatusService.class, url);
    }

    /**
     * 创建BlacktelSendService
     */
    @Bean
    public BlacktelSendService createSendService() {
        return FeignUtil.createJsonClient(BlacktelSendService.class, blacktel_host);
    }

    @Bean
    public BlacktelStatusService createStatusService() {
        return createStatusService(blacktel_host);
    }

}
