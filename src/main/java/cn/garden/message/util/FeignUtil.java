package cn.garden.message.util;

import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;

/**
 * @author liwei
 */
public class FeignUtil {

    /**
     * 创建服务
     * <a href="https://springdoc.cn/spring-cloud-openfeign/#%E6%89%8B%E5%8A%A8%E5%88%9B%E5%BB%BA-feign-client">...</a>
     */
    public static <T> T createJsonClient(Class<T> apiType, String url) {
        Slf4jLogger slf4jLogger = new Slf4jLogger(apiType);
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .logger(slf4jLogger)
                .logLevel(Logger.Level.FULL)
                .target(apiType, url);
    }
}
