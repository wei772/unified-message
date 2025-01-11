package cn.garden.message.client.wecom;


import cn.garden.message.client.wecom.api.WecomService;
import cn.garden.message.client.wecom.api.WecomServiceFactory;
import cn.garden.message.client.wecom.model.*;
import cn.garden.message.util.ExceptionUtil;
import cn.garden.message.util.cache.Cache;
import cn.garden.message.util.cache.MemoryCache;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * 描述: 企业微信客户
 *
 * @author liwei
 */
@Service
public class WecomMessageClient {

    private final static Logger LOGGER = LoggerFactory.getLogger(WecomMessageClient.class);
    private final WecomService wecomService;
    private final Cache<String> cache;

    public WecomMessageClient() {
        this(WecomServiceFactory.createDefault(), new MemoryCache<>());
    }

    @Autowired
    public WecomMessageClient(WecomService wecomService, Cache<String> cache) {
        this.wecomService = wecomService;
        this.cache = cache;
    }

    public WecomSendMessageResponse sendMessage(WecomSendMessageRequest request) {
        request.validate();
        return callApiRetryToken(request,
                (accessToken) -> wecomService.sendMessage(accessToken, request));
    }

    /**
     * 支持重新获取token,重新请求一次
     * <a href="https://developer.work.weixin.qq.com/document/path/91039">...</a>
     * 注意事项：
     * 开发者需要缓存access_token，用于后续接口的调用（注意：不能频繁调用gettoken接口，否则会受到频率拦截）。当access_token失效或过期时，需要重新获取。
     * <p>
     * access_token的有效期通过返回的expires_in来传达，正常情况下为7200秒（2小时），有效期内重复获取返回相同结果，过期后获取会返回新的access_token。
     * 由于企业微信每个应用的access_token是彼此独立的，所以进行缓存时需要区分应用来进行存储。
     * access_token至少保留512字节的存储空间。
     * 企业微信可能会出于运营需要，提前使access_token失效，开发者应实现access_token失效时重新获取的逻辑。
     */
    private <D extends WecomResponseBase> D callApiRetryToken(
            WecomRequestBase requestBase,
            Function<String, D> callApi
    ) {
        String accessToken = getAccessToken(requestBase.getCorpId(), requestBase.getCorpSecret(), false);
        D response = callApi.apply(accessToken);
        if (response.isInvalidToken()) {
            LOGGER.info("访问企业微信token: {}", accessToken);
            accessToken = getAccessToken(requestBase.getCorpId(), requestBase.getCorpSecret(), true);
            LOGGER.info("重新获取后的访问企业微信token: {}", accessToken);
            response = callApi.apply(accessToken);
        }
        if (!response.succeed()) {
            LOGGER.info("访问企业微信失败:{} : {}", response.getErrCode(), response.getErrMsg());
        }

        return response;
    }


    /**
     * 获取accessToken
     */
    private String getAccessToken(String corpId, String appSecret, Boolean refresh) {
        String tokenKey = KeyGenerator.getWecomAccessTokenKey(corpId, appSecret);

        String accessToken;
        if (BooleanUtils.isNotTrue(refresh)) {
            accessToken = cache.get(tokenKey);
            if (StringUtils.isNotEmpty(accessToken)) {
                return accessToken;
            }
        }

        // 获取accessToken
        WecomAccessTokenResponse accessTokenResponse = wecomService.getAccessToken(corpId, appSecret);
        if (!accessTokenResponse.succeed()) {
            throw ExceptionUtil.createDefaultException(accessTokenResponse.getErrMsg());
        }

        accessToken = accessTokenResponse.getAccessToken();
        Integer expiresIn = accessTokenResponse.getExpiresIn() - 60 * 3;
        if (expiresIn > 0) {
            cache.set(tokenKey,
                    accessTokenResponse.getAccessToken(),
                    expiresIn,
                    TimeUnit.SECONDS);
        }

        return accessToken;

    }

    private static class KeyGenerator {
        private static String getWecomAccessTokenKey(String cropId, String appSecret) {
            return String.format("message:wecom:cropId@%s:appSecret@%s", cropId, appSecret);
        }
    }
}
