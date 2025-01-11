package cn.garden.message.client.sms.blacktel;

import cn.garden.message.client.sms.blacktel.api.BlacktelServiceFactory;
import cn.garden.message.client.sms.blacktel.model.BlacktelSmsSendRequest;
import cn.garden.message.client.sms.blacktel.model.BlacktelSmsSendResponse;
import cn.garden.message.client.sms.blacktel.model.BlacktelSmsStatusRequest;
import cn.garden.message.client.sms.blacktel.model.BlacktelSmsStatusResponse;
import cn.garden.message.util.RandomStringUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cn.garden.message.config.SenderConfig.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author liwei
 */
public class BlacktelSmsClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlacktelSmsClientTest.class);

    /**
     * 异常1:
     * java.lang.IllegalArgumentException: URI is not absolute
     */
    @Test
    public void sendNullPassword() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            BlacktelSmsClient blacktelSmsClient = new BlacktelSmsClient();
            BlacktelSmsSendRequest request = new BlacktelSmsSendRequest();
            request.setEprId(getBlacktelEprIdKey());
            request.setUserId(getBlacktelUseridKey());
            request.setPassword(null);
            request.setMobile(getBlacktelPhone());
            request.setContent("测试");
            blacktelSmsClient.send(request);
        });
        LOGGER.warn("sendNullPassword", runtimeException);
    }


    @Test
    public void send() {
        BlacktelSmsClient blacktelSmsClient = new BlacktelSmsClient();
        BlacktelSmsSendRequest request = new BlacktelSmsSendRequest();
        request.setEprId(getBlacktelEprIdKey());
        request.setUserId(getBlacktelUseridKey());
        request.setPassword(getBlacktelPasswordKey());
        request.setMobile(getBlacktelPhone());
        request.setContent(RandomStringUtil.randomAlphanumeric(12));
        BlacktelSmsSendResponse sendResponse = blacktelSmsClient.send(request);

        assertTrue(sendResponse.isSucceed());
    }


    @Test
    @Disabled
    public void sendWithBlacktelService() {
        BlacktelSmsClient blacktelSmsClient = new BlacktelSmsClient(
                BlacktelServiceFactory.createSendService(getBlacktelHost()),
                BlacktelServiceFactory.createStatusService(getBlacktelHost())
        );
        BlacktelSmsSendRequest request = new BlacktelSmsSendRequest();
        request.setEprId(getBlacktelEprIdKey());
        request.setUserId(getBlacktelUseridKey());
        request.setPassword(getBlacktelPasswordKey());
        request.setMobile(getBlacktelPhone());
        request.setContent(RandomStringUtil.randomAlphanumeric(12));
        BlacktelSmsSendResponse sendResponse = blacktelSmsClient.send(request);

        assertTrue(sendResponse.isSucceed());
    }

    @Test
    @Disabled
    public void statusWithBlacktelService() {
        BlacktelSmsClient blacktelSmsClient = new BlacktelSmsClient(
                BlacktelServiceFactory.createSendService(getBlacktelHost()),
                BlacktelServiceFactory.createStatusService(getBlacktelHost())
        );
        BlacktelSmsStatusRequest request = new BlacktelSmsStatusRequest();
        request.setEprId(getBlacktelEprIdKey());
        request.setUserId(getBlacktelUseridKey());
        request.setPassword(getBlacktelPasswordKey());

        BlacktelSmsStatusResponse sendResponse = blacktelSmsClient.getStatus(request);

        assertTrue(sendResponse.isSucceed());
    }

    @Test
    public void getSmsStatusNullUserId() {
        BlacktelSmsClient blacktelSmsClient = new BlacktelSmsClient();
        BlacktelSmsStatusRequest request = new BlacktelSmsStatusRequest();
        request.setEprId(getBlacktelEprIdKey());
        request.setUserId(null);
        request.setPassword(getBlacktelPasswordKey());

        BlacktelSmsStatusResponse statusResponse = blacktelSmsClient.getStatus(request);

        assertFalse(statusResponse.isSucceed());
    }

    /**
     * 异常1：
     * com.fasterxml.jackson.core.JsonParseException: Unexpected character ('<' (code 60)): expected a valid value (JSON String, Number, Array, Object or token 'null', 'true' or 'false')
     * at [Source: REDACTED (`StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION` disabled); line: 1, column: 1]
     * 返回xml IOUtils.toString(response.body().asInputStream(),"utf-8")
     * <returnsms><count>0</count></returnsms>
     * 不使用JacksonDecoder
     * <p>
     * 异常2：
     * jakarta.xml.bind.JAXBException: Implementation of Jakarta XML Binding-API has not been found on module path or classpath.
     * 引入 jaxb-runtime
     */
    @Test
    public void getSmsStatus() {
        BlacktelSmsClient blacktelSmsClient = new BlacktelSmsClient();
        BlacktelSmsStatusRequest request = new BlacktelSmsStatusRequest();
        request.setEprId(getBlacktelEprIdKey());
        request.setUserId(getBlacktelUseridKey());
        request.setPassword(getBlacktelPasswordKey());

        BlacktelSmsStatusResponse statusResponse = blacktelSmsClient.getStatus(request);

        assertTrue(statusResponse.isSucceed());
    }
}
